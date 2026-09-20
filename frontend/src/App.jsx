import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Signin from "./Signin";
import Signup from "./Signup";
import Chat from "./Chat";

function App() {
    return (
        <BrowserRouter>
            <Routes>

                <Route
                    path="/"
                    element={<Navigate to="/login" replace />}
                />

                <Route
                    path="/login"
                    element={<Signin />}
                />

                <Route
                    path="/signup"
                    element={<Signup />}
                />

                <Route
                    path="/chat"
                    element={<Chat />}
                />

            </Routes>
        </BrowserRouter>
    );
}

export default App;