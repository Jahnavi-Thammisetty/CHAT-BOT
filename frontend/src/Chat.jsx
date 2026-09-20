import React, { useEffect, useState } from "react";
import "./chat.css";
import { apiFetch } from "./apiFetch";

function Chat() {
    const [message, setMessage] = useState("");
    const [chats, setChats] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const userId = localStorage.getItem("Id");

    console.log("User ID:", userId);

    // --------------------------------------------------
    // Load previous chats when Chat page opens
    // --------------------------------------------------

    useEffect(() => {

        const loadChats = async () => {

            try {

                setError("");

                const response = await apiFetch(
                    `/v1/chat/${userId}`
                );

                const data = await response.json();

                if (!response.ok) {
                    throw new Error(
                        data?.message ||
                        "Failed to load chats"
                    );
                }

                setChats(data);

            } catch (error) {

                console.error(
                    "Error loading chats:",
                    error
                );

                setError(
                    error.message ||
                    "Failed to load previous chats"
                );
            }
        };

        if (userId) {
            loadChats();
        } else {
            setError(
                "User ID not found. Please login again."
            );
        }

    }, [userId]);


    // --------------------------------------------------
    // Send new message
    // --------------------------------------------------

    const handleSend = async (e) => {
    e.preventDefault();

    if (!message.trim()) {
        return;
    }

    if (!userId) {
        setError("User ID not found. Please login again.");
        return;
    }

    setError("");

    const userMessage = message.trim();

    // Temporary ID for this UI message
    const tempId = Date.now().toString();

    // Show user's question immediately
    const temporaryChat = {
        id: tempId,
        userId: userId,
        userMessage: userMessage,
        aiResponse: "",
        isQuestion: true,
        isAnswer: false
    };

    setChats((previousChats) => [
        ...previousChats,
        temporaryChat
    ]);

    // Clear input immediately
    setMessage("");

    setLoading(true);

    try {

        const response = await apiFetch(
            "/v1/chat/",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    userId: userId,
                    userMessage: userMessage,
                    isQuestion: true
                })
            }
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(
                data?.message || "Failed to send message"
            );
        }

        console.log("POST response:", data);

        // Replace temporary message with backend response
        setChats((previousChats) =>
        previousChats.map((chat) =>
            chat.id === tempId
                ? {
                    ...chat,
                    ...data,
                    userMessage: userMessage,
                    isQuestion: true
                }
                : chat
        )
    );

    } catch (error) {

        console.error(
            "Error sending message:",
            error
        );

        setError(
            error.message || "Something went wrong"
        );

        // Remove temporary message if POST fails
        setChats((previousChats) =>
            previousChats.filter(
                (chat) => chat.id !== tempId
            )
        );

        // Put message back
        setMessage(userMessage);

    } finally {

        setLoading(false);
    }
};


    // --------------------------------------------------
    // UI
    // --------------------------------------------------

    return (
        <div className="chat-page">

            {/* Header */}
            <div className="chat-header">

                <h2>
                    AI Chat
                </h2>

            </div>


            {/* Error */}
            {error && (
                <div className="chat-error">
                    {error}
                </div>
            )}


            {/* Messages */}
            <div className="chat-messages">

                {/* Empty chat */}
                {chats.length === 0 && !loading && (
                    <div className="empty-chat">

                        <h3>
                            How can I help you?
                        </h3>

                        <p>
                            Ask me anything to get started.
                        </p>

                    </div>
                )}


                {/* Chat messages */}
                {chats.map((chat, index) => (

                    <React.Fragment
                        key={chat.id || index}
                    >

                        {/* User Question - RIGHT */}
                        {chat.isQuestion &&
                            chat.userMessage && (

                                <div
                                    className="message-row user-row"
                                >

                                    <div
                                        className="message user-message"
                                    >
                                        {chat.userMessage}
                                    </div>

                                </div>
                            )
                        }


                        {/* AI Answer - LEFT */}
                        {chat.isAnswer &&
                            chat.aiResponse && (

                                <div
                                    className="message-row ai-row"
                                >

                                    <div
                                        className="message ai-message"
                                    >
                                        {chat.aiResponse}
                                    </div>

                                </div>
                            )
                        }

                    </React.Fragment>
                ))}


                {/* Loading */}
                {loading && (

                    <div
                        className="message-row ai-row"
                    >

                        <div
                            className="message ai-message"
                        >
                            Thinking...
                        </div>

                    </div>
                )}

            </div>


            {/* Message Input */}
            <form
                className="chat-input-container"
                onSubmit={handleSend}
            >

                <input
                    type="text"
                    placeholder="Ask something..."
                    value={message}
                    onChange={(e) =>
                        setMessage(e.target.value)
                    }
                    disabled={loading}
                />

                <button
                    type="submit"
                    disabled={
                        loading ||
                        !message.trim()
                    }
                >

                    {loading
                        ? "Sending..."
                        : "Send"}

                </button>

            </form>

        </div>
    );
}
export default Chat;





