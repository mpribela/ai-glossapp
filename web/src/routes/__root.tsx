import Button from "@mui/material/Button";
import {createRootRouteWithContext, Link, Outlet} from "@tanstack/react-router";
import {TanStackRouterDevtools} from "@tanstack/react-router-devtools";
import type {QueryClient} from "@tanstack/react-query";
import type {AuthContext} from "../main.tsx";
import {Box, CssBaseline, Stack} from "@mui/material";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import HomeIcon from '@mui/icons-material/Home';

interface MyRouterContext {
    queryClient: QueryClient
    auth: AuthContext
}

const RootLayout = () => (
    <>
        <Header/>
        <CssBaseline/>
        <Outlet/>
        <TanStackRouterDevtools/>
    </>
);

function Header() {
    const {isAuthenticated, logout} = useAuthenticationUser();
    return (
        <Box height="3rem" display="flex" borderBottom="1px solid #006494" alignItems="center">
            <Stack direction="row" marginX="1rem" width="100%" height="100%">
                <Button component={Link} to={"/"} startIcon={<HomeIcon/>} sx={{paddingX: "1rem"}}>Home</Button>
                {isAuthenticated && <>
                    <Button component={Link} variant="text" to="/topics" sx={{paddingX: "1rem"}}>Topics</Button>
                    <Button variant="text" onClick={() => logout()}
                            sx={{marginLeft: "auto", paddingX: "1rem"}}>Logout</Button>
                </>}
            </Stack>
        </Box>
    );
}

export const Route = createRootRouteWithContext<MyRouterContext>()({component: RootLayout});
