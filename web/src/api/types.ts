export type TopicDto = { id: number, topic: string, learnerId: number }
export type TopicListDto = { topics: TopicDto[] }

const apiBaseUrl = import.meta.env.VITE_BASE_URL;

export const api = {
    topics: apiBaseUrl + '/api/topic/all',
    createTopic: apiBaseUrl + '/api/topic',
}