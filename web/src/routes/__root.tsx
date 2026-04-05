import Button from "@mui/material/Button";
import {createRootRouteWithContext, Link, Outlet} from "@tanstack/react-router";
import {TanStackRouterDevtools} from "@tanstack/react-router-devtools";
import type {QueryClient} from "@tanstack/react-query";
import type {AuthContext} from "../main.tsx";

interface MyRouterContext {
    queryClient: QueryClient
    auth: AuthContext
}

const RootLayout = () => (
    <>
        <div className="p-2 flex gap-2">
            <Link to="/dashboard" className="[&.active]:font-bold">
                <Button size="large">Dashboard</Button>
            </Link>{" "}
        </div>
        <hr/>
        <Outlet/>
        <TanStackRouterDevtools/>
    </>
);

export const Route = createRootRouteWithContext<MyRouterContext>()({component: RootLayout});
