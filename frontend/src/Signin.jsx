import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./auth.css";

function Signin() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError("");
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8202/v1/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(
                    data?.message || "Invalid email or password"
                );
            }

            console.log("Login successful:", data);
            localStorage.setItem("Id", data.responseObject.id);  
            // Navigate after successful login
            navigate("/chat");

        } catch (error) {
            console.error("Login error:", error);

            setError(
                error.message || "Something went wrong. Please try again."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-page">

            <div className="auth-card">

                {/* Logo */}
                <div className="logo">
                    AI
                </div>

                {/* Header */}
                <div className="auth-header">
                    <h1>Welcome back</h1>

                    <p>
                        Sign in to continue your conversation
                    </p>
                </div>

                {/* Error Message */}
                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

                {/* Sign In Form */}
                <form onSubmit={handleSubmit}>

                    {/* Email */}
                    <div className="form-group">

                        <label htmlFor="email">
                            Email
                        </label>

                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Enter your email"
                            autoComplete="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />

                    </div>

                    {/* Password */}
                    <div className="form-group">

                        <div className="label-row">

                            <label htmlFor="password">
                                Password
                            </label>

                            <a
                                href="#"
                                className="forgot-password"
                                onClick={(e) => e.preventDefault()}
                            >
                                Forgot password?
                            </a>

                        </div>

                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Enter your password"
                            autoComplete="current-password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />

                    </div>

                    {/* Login Button */}
                    <button
                        type="submit"
                        className="primary-button"
                        disabled={loading}
                    >
                        {loading ? "Signing in..." : "Sign In"}
                    </button>

                </form>

                {/* Signup Link */}
                <div className="auth-footer">

                    <span>
                        Don't have an account?
                    </span>

                    <a href="/signup">
                        Sign up
                    </a>

                </div>

            </div>

        </div>
    );
}

export default Signin;