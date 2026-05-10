import {createFileRoute, redirect} from '@tanstack/react-router'
import {TopicPage} from "../../pages/topics/TopicPage.tsx";

export const Route = createFileRoute('/topics/$topicId')({
    beforeLoad: async ({context}) => {
        if (!context.auth.isAuthenticated) {
            throw redirect({to: "/"})
        }
    },
    component: TopicPage,
})