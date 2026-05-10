import {useParams} from "@tanstack/react-router"
import {useQuery} from "@tanstack/react-query";
import {api, type TopicDetailDto} from "../../api/types.ts";
import {useAuthenticationUser} from "../../hooks/useAuthenticationUser.ts";
import {useEffect} from "react";

export function TopicPage() {
    const {topicId} = useParams({from: "/topics/$topicId"})
    const {accessToken} = useAuthenticationUser()
    const {data} = useQuery<TopicDetailDto>({
        queryKey: [topicId],
        queryFn: async () => {
            const response = await fetch(api.getTopic(topicId), {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            return response.json()
        },
        enabled: !!accessToken,
    });
    useEffect(() => {
        if (data !== undefined) {
            console.log(data)
        }
    }, [data])
    return (<></>)
}