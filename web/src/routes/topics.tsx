import {createFileRoute, redirect} from "@tanstack/react-router";
import TopicsPage from "../pages/TopicsPage.tsx";

export const Route = createFileRoute("/topics")({
    beforeLoad: async ({context}) => {
        if (!context.auth.isAuthenticated) {
            throw redirect({to: "/"})
        }
    },
    component: TopicsPage,
});
