import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {RouterProvider, createRouter} from "@tanstack/react-router";
import {ThemeProvider, createTheme} from "@mui/material/styles";
import {Auth0Provider} from "@auth0/auth0-react";

// Import the generated route tree
import { routeTree } from "./routeTree.gen";

const queryClient = new QueryClient();

// Create a new router instance with query client
const router = createRouter({ routeTree, context: { queryClient } });

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

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <ThemeProvider theme={theme}>
            <QueryClientProvider client={queryClient}>
                <Auth0Provider
                    domain={import.meta.env.VITE_AUTH0_DOMAIN}
                    clientId={import.meta.env.VITE_AUTH0_CLIENT_ID}
                    authorizationParams={{
                        redirect_uri: window.location.origin,
                        audience: import.meta.env.VITE_AUTH0_AUDIENCE
                    }}
                >
                    <RouterProvider router={router} />
                </Auth0Provider>
            </QueryClientProvider>
        </ThemeProvider>
    </StrictMode>
);
