import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Signin from "./Signin";
import Signup from "./Signup";
import Chat from "./Chat";

function App() {
    return (
        <BrowserRouter>

            <Routes>

                {/* Default URL */}
                <Route
                    path="/"
                    element={
                        <Navigate
                            to="/signin"
                            replace
                        />
                    }
                />

                {/* Sign In */}
                <Route
                    path="/signin"
                    element={<Signin />}
                />

                {/* Sign Up */}
                <Route
                    path="/signup"
                    element={<Signup />}
                />

                {/* Chat */}
                <Route
                    path="/chat"
                    element={<Chat />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;