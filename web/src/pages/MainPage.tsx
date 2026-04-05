import {Typography} from "@mui/material";
import {useAuth0} from "@auth0/auth0-react";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import {useQuery} from "@tanstack/react-query";
import type {TopicListDto} from "../api/types.ts";

export default function MainPage() {
    const {
        isLoading,
        error,
        loginWithRedirect: login,
        logout: auth0Logout,
    } = useAuth0();

    const { user, accessToken, isAuthenticated } = useAuthenticationUser();
    const signup = () =>
        login({ authorizationParams: { screen_hint: "signup" } });

    const logout = () =>
        auth0Logout({ logoutParams: { returnTo: window.location.origin } });

    const { data, isSuccess } = useQuery<TopicListDto>({
        queryKey: ['topics'],
        queryFn: async () => {
            const response = await fetch('http://localhost:8080/api/topic/all', {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            return response.json();
        },
        enabled: isAuthenticated && accessToken !== null,
    })

    if (isLoading) return "Loading...";


    return isAuthenticated ? (
        <>
            <Typography>Welcome {user!.email}!</Typography>
            <button onClick={logout}>Logout</button>
            {isSuccess ? data!.topics.map(topic => (
                <Typography key={topic.id}>{topic.topic}</Typography>
            )) : "Loading..."}
        </>
    ) : (
        <>
            {error && <p>Error: {error.message}</p>}
            <button onClick={signup}>Signup</button>
            <button onClick={() => login()}>Login</button>
        </>
    )
}