export type TopicCreatedDTO = {
    id: number
}

export type TopicDetailDto = {
    topic: string,
    description?: string,
    sourceLanguage: string,
    targetLanguage: string,
    proficiencyLevel: string,
    verbs: VerbDto[]
    nouns: NounDto[]
    adverbs: AdverbDto[]
    adjectives: AdjectiveDto[]
}

export type AdjectiveDto = {
    id: number
    adjective: string
    translation: string
    notes: string
    examples: ExampleSentence[]
}

export type AdverbDto = {
    id: number
    adverb: string
    translation: string
    notes: string
    examples: ExampleSentence[]
}

export type VerbDto = {
    id: number
    verb: string
    translation: string
    isRegular?: boolean
    forms: VerbForm[]
    notes: string
    examples: ExampleSentence[]
}

export type NounDto = {
    nounArticle: string
    indefiniteSingularFormNoun: string
    definitiveSingularFormNoun: string
    indefinitePluralFormNoun: string
    definitivePluralFormNoun: string
    isCountable: boolean
    translation: string
    notes: string
    examples: ExampleSentence[]
}

export type ExampleSentence = {
    sentence: string
    translation: string
}

export type VerbForm = {
    form: string
    formType: string
}

export type TopicDto = { id: number, topic: string, description: string, learnerId: number }
export type TopicListDto = { topics: TopicDto[] }

const apiBaseUrl = import.meta.env.VITE_BASE_URL;

export const api = {
    topics: apiBaseUrl + '/api/topic/all',
    createTopic: apiBaseUrl + '/api/topic',
    deleteTopic: (id: string) => apiBaseUrl + `/api/topic/${id}`,
    getTopic: (id: string) => apiBaseUrl + `/api/topic/${id}`,
}