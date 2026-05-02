import {Box, CircularProgress, Stack, Typography} from "@mui/material";
import {useAuth0} from "@auth0/auth0-react";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import Button from "@mui/material/Button";

export default function MainPage() {
    const {
        isLoading,
        error,
        loginWithRedirect: login,
    } = useAuth0();

    const {user, isAuthenticated} = useAuthenticationUser();
    const signup = () =>
        login({authorizationParams: {screen_hint: "signup"}});

    if (isLoading) return (
        <Box
            sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                width: "100%",
                height: "calc(100vh - 4rem)"
            }}>
            <CircularProgress/>
        </Box>);

    return isAuthenticated ? (
        <Box
            sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                height: "calc(100vh - 4rem)",
                width: "100%"
            }}>
            <Typography>Welcome {user!.email}!</Typography>
        </Box>
    ) : (
        <Box
            sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                minHeight: "100vh",
                width: "100%",
                height: "100%"
            }}>
            <Stack direction="column">
                <Typography variant="h6" sx={{textAlign: "center"}}>Log in to continue</Typography>
                <Stack direction="row" spacing={2} sx={{justifyContent: "center", marginTop: "1rem"}}>
                    {error && <p>Error: {error.message}</p>}
                    <Button variant="contained" onClick={() => login()}>Login</Button>
                    <Button variant="contained" onClick={() => signup()}>Signup</Button>
                </Stack>
            </Stack>
        </Box>
    )
}