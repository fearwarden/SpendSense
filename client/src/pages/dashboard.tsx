import { Card } from "@/components/ui/card";
import { SidebarProvider } from "@/components/ui/sidebar";
import React from "react";
import { AppSidebar } from "../components/sidebar";

const Dashboard: React.FC = () => {
  return (
    <div className="flex">
      <SidebarProvider>
        <AppSidebar />
        <main className="flex-1 p-6">
          <Card></Card>
        </main>
      </SidebarProvider>
    </div>
  );
};

export default Dashboard;
