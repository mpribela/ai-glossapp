import {useMutation, useQuery} from "@tanstack/react-query";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import {Box, Card, CardActions, CardContent, CircularProgress, Stack, Typography} from "@mui/material";
import {api, type TopicListDto} from "../api/types.ts";
import Button from "@mui/material/Button";
import {Link} from "@tanstack/react-router";
import * as React from "react";

export default function TopicsPage() {
    const {accessToken} = useAuthenticationUser();
    const {data, isSuccess, isPending, refetch} = useQuery<TopicListDto>({
        queryKey: ["topics"],
        queryFn: async () => {
            const response = await fetch(api.topics, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            return response.json();
        },
        enabled: !!accessToken,
    })
    const {mutate: removeTopic} = useMutation<void, Error, string>({
        mutationFn: async (topicId: string) => {
            const response = await fetch(api.deleteTopic(topicId), {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            return response.json();
        },
        onSuccess: () => refetch()
    });
    return (
        <Box
            sx={{
                padding: "0",
                margin: "0",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                width: "100%",
                height: "100%"
            }}>
            {isPending || accessToken === undefined
                ?
                <Box sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    height: "calc(100vs - 4rem)",
                    width: "100%"
                }}>
                    <CircularProgress/>
                </Box>
                : isSuccess
                    ? <Box sx={{margin: "2rem"}}>
                        <Stack
                            direction="row"
                            spacing={2}
                            useFlexGap
                            sx={{
                                flexWrap: 'wrap',
                                overflow: "hidden",
                                padding: "1rem",
                                justifyContent: "center",
                            }}>
                            {data.topics.map(topic => (
                                <Card sx={{
                                    width: "20rem",
                                    height: "10rem",
                                    marginBottom: "2rem",
                                    '&:hover': {
                                        backgroundColor: 'primary.dark', // Use theme color
                                        color: '#fff',                  // Standard CSS
                                        cursor: 'pointer',
                                    }
                                }} component={Link}
                                      to={`/topics/${topic.id}`}>
                                    <CardContent>
                                        <Typography variant="h5" component="div">
                                            {topic.topic}
                                        </Typography>
                                        <Typography variant="body2">
                                            {topic.description}
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button
                                            size="small"
                                            variant="contained"
                                            onClick={(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
                                                removeTopic(String(topic.id))
                                                event.stopPropagation()
                                                event.preventDefault()
                                            }}
                                            sx={{
                                                marginLeft: "0.5rem"
                                            }}>
                                            Remove
                                        </Button>
                                    </CardActions>
                                </Card>
                            ))}
                            <Button variant="outlined"
                                    component={Link}
                                    to={"/topics/new"}
                                    sx={{
                                        width: "10rem",
                                        height: "10rem",
                                        paddingTop: "1rem",
                                        padding: "1rem",
                                        textAlign: "center"
                                    }}>Create Topic</Button>
                        </Stack>
                    </Box>
                    : <Typography>Unknown error occurred</Typography>}
        </Box>
    )
}