import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {createRouter, RouterProvider} from "@tanstack/react-router";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import {Auth0Provider} from "@auth0/auth0-react";
import {useAuthenticationUser} from "./hooks/useAuthenticationUser.ts";

// Import the generated route tree
import {routeTree} from "./routeTree.gen";
import {CircularProgress} from "@mui/material";

const queryClient = new QueryClient();

export interface AuthContext {
    isAuthenticated: boolean;
    isLoading: boolean;
}

// Create a new router instance with query client
const router = createRouter({
    routeTree,
    context: {
        queryClient,
        auth: undefined!
    }
});

// Register the router instance for type safety
declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

const theme = createTheme({
    typography: {
        fontFamily: ["Roboto", "sans-serif"].join(","),
    },
    palette: {},
});

export function InnerApp() {
    const {isAuthenticated, isLoading} = useAuthenticationUser();
    if (isLoading) {
        return <CircularProgress/>;
    }
    return <RouterProvider router={router} context={{queryClient, auth: {isAuthenticated, isLoading}}}/>;
}

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <ThemeProvider theme={theme}>
            <QueryClientProvider client={queryClient}>
                <Auth0Provider
                    domain={import.meta.env.VITE_AUTH0_DOMAIN}
                    clientId={import.meta.env.VITE_AUTH0_CLIENT_ID}
                    cacheLocation="localstorage"
                    authorizationParams={{
                        redirect_uri: window.location.origin,
                        audience: import.meta.env.VITE_AUTH0_AUDIENCE
                    }}
                >
                    <InnerApp/>
                </Auth0Provider>
            </QueryClientProvider>
        </ThemeProvider>
    </StrictMode>
);
