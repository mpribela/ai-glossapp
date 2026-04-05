import {Box, Stack, Typography} from "@mui/material";
import {useAuth0} from "@auth0/auth0-react";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import {useQuery} from "@tanstack/react-query";
import type {TopicListDto} from "../api/types.ts";
import Button from "@mui/material/Button";

export default function MainPage() {
    const {
        isLoading,
        error,
        loginWithRedirect: login,
        logout: auth0Logout,
    } = useAuth0();

    const {user, accessToken, isAuthenticated} = useAuthenticationUser();
    const signup = () =>
        login({authorizationParams: {screen_hint: "signup"}});

    const logout = () =>
        auth0Logout({logoutParams: {returnTo: window.location.origin}});

    const {data, isSuccess} = useQuery<TopicListDto>({
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
            {isSuccess ? data!.topics.map(topic => (
                    <Typography key={topic.id}>{topic.topic}</Typography>
                )) :
                <Typography>Loading...</Typography>}
            <Button variant="contained" onClick={() => logout()}>Logout</Button>
        </>
    ) : (
        <Box display="flex"
             justifyContent="center"
             alignItems="center"
             minHeight="100vh">
            <Stack direction="column">
                <Typography variant="h6" textAlign="center">Log in to continue</Typography>
                <Stack direction="row" spacing={2} justifyContent="center" marginTop={2}>
                    {error && <p>Error: {error.message}</p>}
                    <Button variant="contained" onClick={() => login()}>Login</Button>
                    <Button variant="contained" onClick={() => signup()}>Signup</Button>
                </Stack>
            </Stack>
        </Box>
    )
}