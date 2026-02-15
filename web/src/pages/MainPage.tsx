import { useNavigate } from "@tanstack/react-router";

export default function MainPage() {
    const navigate = useNavigate();
    return (
            <button onClick={() => {
                navigate({
                    to: "/login"
                })
            }}>Login</button>
    )
}