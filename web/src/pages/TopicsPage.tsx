import {useQuery} from "@tanstack/react-query";
import {useAuthenticationUser} from "../hooks/useAuthenticationUser.ts";
import {Box, Card, CardActions, CardContent, CircularProgress, Stack, Typography} from "@mui/material";
import {api, type TopicListDto} from "../api/types.ts";
import Button from "@mui/material/Button";
import {Link} from "@tanstack/react-router";

export default function TopicsPage() {
    const {accessToken} = useAuthenticationUser();
    const {data, isSuccess, isPending} = useQuery<TopicListDto>({
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
                            sx={{
                                flexWrap: 'wrap',
                                overflow: "hidden",
                                padding: "1rem",
                                useFlexGap: "true",
                                justifyContent: "center"
                            }}>
                            {data.topics.map(topic => (
                                <Card sx={{width: "10rem", height: "10rem", paddingTop: "1rem"}}>
                                    <CardContent>
                                        <Typography variant="h5" component="div">
                                            {topic.topic}
                                        </Typography>
                                        <Typography variant="body2">
                                            Topic description
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small">Open topic</Button>
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