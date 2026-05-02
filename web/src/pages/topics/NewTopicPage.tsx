import {Box, Checkbox, FormControlLabel, MenuItem, Select, Stack, TextField, Typography} from "@mui/material";
import Button from "@mui/material/Button";
import {z} from "zod";
import {Controller, type SubmitHandler, useForm} from "react-hook-form"
import {standardSchemaResolver} from "@hookform/resolvers/standard-schema";
import {useMutation} from "@tanstack/react-query";
import {api} from "../../api/types.ts";
import {useAuthenticationUser} from "../../hooks/useAuthenticationUser.ts";

const formSchema = z.object({
    topic: z.string().min(3, "Topic must be at least 3 characters long."),
    description: z.string().max(100, "Description cannot be longer than 100 characters.").optional(),
    getAISuggestions: z.boolean(),
    sourceLanguage: z.string(),
    targetLanguage: z.string(),
    proficiencyLevel: z.string(),
})

type Schema = z.infer<typeof formSchema>

export function NewTopicPage() {
    const {accessToken} = useAuthenticationUser();

    const mutation = useMutation({
        mutationFn: async (data: Schema) => {
            const response = await fetch(api.createTopic, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + accessToken
                },
                body: JSON.stringify(data),
            })
            if (!response.ok) {
                //todo error handling
                console.log(response.status, response)
            } else {
                console.log("OK", response)
            }
            return response.json()
        },
    })

    const {handleSubmit, register, control, formState: {errors, isSubmitting}} = useForm<Schema>({
        defaultValues: {
            topic: "",
            description: "",
            getAISuggestions: true,
            sourceLanguage: "English",
            targetLanguage: "Norwegian (Bokmål)",
            proficiencyLevel: "B1"
        },
        resolver: standardSchemaResolver(formSchema)
    })

    const onSubmit: SubmitHandler<Schema> = (data: Schema) => {
        mutation.mutate(data)
    }

    return (
        <Box sx={{display: "flex", alignItems: "center", justifyContent: "center", width: "100%", height: "100%"}}>
            <Stack component="form"
                   direction="column"
                   spacing={2}
                   sx={{width: "20rem", marginTop: "2rem"}}
                   onSubmit={handleSubmit(onSubmit)}>
                <TextField
                    label="Topic"
                    size="small"
                    error={!!errors.topic}
                    helperText={errors.topic?.message}
                    {...register("topic")}
                />
                <TextField
                    label="Description" size="small"
                    error={!!errors.description}
                    helperText={errors.description?.message}
                    multiline
                    rows={4}
                    {...register("description")}
                />
                <Typography>Source language: </Typography>
                <Controller
                    name="sourceLanguage"
                    control={control}
                    render={({field}) => (
                        <Select
                            size="small"
                            value={field.value}
                            sx={{marginRight: "1rem"}}
                            label="Source language"
                            onChange={(e) => field.onChange(e.target.value)}>
                            <MenuItem value={"English"}>English</MenuItem>
                        </Select>
                    )}
                />
                <Typography>Target language: </Typography>
                <Controller
                    name="targetLanguage"
                    control={control}
                    render={({field}) => (
                        <Select
                            size="small"
                            value={field.value}
                            label="Target language"
                            onChange={(e) => field.onChange(e.target.value)}>
                            <MenuItem value={"Norwegian (Bokmål)"}>Norwegian (Bokmål)</MenuItem>
                        </Select>
                    )}
                />
                <Typography>Language proficiency: </Typography>
                <Controller
                    name="proficiencyLevel"
                    control={control}
                    render={({field}) => (
                        <Select
                            size="small"
                            value={field.value}
                            label="Language proficiency"
                            onChange={(e) => field.onChange(e.target.value)}>
                            <MenuItem value={"A1"}>A1</MenuItem>
                            <MenuItem value={"A2"}>A2</MenuItem>
                            <MenuItem value={"B1"}>B1</MenuItem>
                            <MenuItem value={"B2"}>B2</MenuItem>
                            <MenuItem value={"C1"}>C1</MenuItem>
                        </Select>
                    )}
                />
                <Controller
                    name="getAISuggestions"
                    control={control}
                    render={({field}) => (
                        <FormControlLabel
                            control={
                                <Checkbox
                                    size="small"
                                    checked={field.value}
                                    onChange={(e) => field.onChange(e.target.checked)}
                                    onBlur={field.onBlur}/>
                            } label="Get suggestions with AI"/>
                    )}
                />
                <Button size="small" variant="contained" type="submit" disabled={isSubmitting}>Create</Button>
            </Stack>
        </Box>
    )
}