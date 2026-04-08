import {Box, Checkbox, FormControlLabel, Stack, TextField} from "@mui/material";
import Button from "@mui/material/Button";

// todo add useForm and saving of the topic

export function NewTopicPage() {
    return (
        <Box display="flex" alignItems="center" justifyContent="center" width="100%" height="100%">
            <Stack direction="column" spacing={2} width="20rem" marginTop="2rem">
                <TextField label="Topic" size="small"/>
                <TextField label="Description" size="small" multiline rows={4}/>
                <FormControlLabel control={<Checkbox defaultChecked/>} label="Get suggestions with AI"/>
                <Button size="small" variant="contained">Create</Button>
            </Stack>
        </Box>
    )
}