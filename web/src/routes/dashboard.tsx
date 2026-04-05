import {createFileRoute, redirect} from "@tanstack/react-router";
import DashboardPage from "../pages/DashboardPage.tsx";

export const Route = createFileRoute("/dashboard")({
    beforeLoad: async ({context}) => {
        if (!context.auth.isAuthenticated) {
            throw redirect({to: "/"})
        }
    },
    component: DashboardPage,
});
