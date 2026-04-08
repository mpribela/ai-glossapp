import {createFileRoute, redirect} from '@tanstack/react-router'
import {NewTopicPage} from "../../pages/topics/NewTopicPage.tsx";

export const Route = createFileRoute('/topics/new')({
    beforeLoad: async ({context}) => {
        if (!context.auth.isAuthenticated) {
            throw redirect({to: "/"})
        }
    },
    component: NewTopicPage,
})
