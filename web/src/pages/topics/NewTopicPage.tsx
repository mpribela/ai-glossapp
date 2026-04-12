import {Box, Checkbox, FormControlLabel, Stack, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import {z} from "zod";
import {Controller, type SubmitHandler, useForm} from "react-hook-form"
import {standardSchemaResolver} from "@hookform/resolvers/standard-schema";

const formSchema = z.object({
    topic: z.string().min(3, "Topic must be at least 3 characters long."),
    description: z.string().max(100, "Description cannot be longer than 100 characters.").optional(),
    suggestions: z.boolean(),
})

type Schema = z.infer<typeof formSchema>

export function NewTopicPage() {

    const {handleSubmit, register, control, formState: {errors, isSubmitting}} = useForm<Schema>({
        defaultValues: {
            topic: "",
            description: "",
            suggestions: true,
        },
        resolver: standardSchemaResolver(formSchema)
    })
    //todo add call to api
    const onSubmit: SubmitHandler<Schema> = (data) => {
        console.log(data)
    }

    return (
        <Box display="flex" alignItems="center" justifyContent="center" width="100%" height="100%">
            <Stack component="form" direction="column" spacing={2} width="20rem" marginTop="2rem"
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
                <Controller
                    name="suggestions"
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