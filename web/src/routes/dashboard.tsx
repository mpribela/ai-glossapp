import { createFileRoute } from "@tanstack/react-router";
import DashboardPage from "../pages/DashboardPage.tsx";

export const Route = createFileRoute("/dashboard")({
  component: DashboardPage,
});
