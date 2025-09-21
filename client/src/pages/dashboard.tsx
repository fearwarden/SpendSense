import { AppSidebar } from "@/components/app-sidebar";
import { ChartBarMultiple } from "@/components/chart-area-interactive";
import { ChartPieDonut } from "@/components/chart-pie-donut";
import { DataTable } from "@/components/data-table";
import { SiteHeader } from "@/components/site-header";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { CircleDollarSign, Terminal, X } from "lucide-react";
import { useEffect, useState } from "react";
import data from "./data.json";

export default function Page() {
  const [showAlert, setShowAlert] = useState<boolean>(false);

  const displayAlert = () => {
    const rand = Math.floor(Math.random() * (90 - 20 + 1)) + 20;

    setTimeout(() => {
      setShowAlert(true);
    }, rand * 1000);
  };
  useEffect(() => {
    displayAlert();
  }, []);
  return (
    <SidebarProvider
      style={
        {
          "--sidebar-width": "calc(var(--spacing) * 72)",
          "--header-height": "calc(var(--spacing) * 12)",
        } as React.CSSProperties
      }
    >
      <AppSidebar variant="inset" />
      <SidebarInset>
        <SiteHeader />
        {showAlert && (
          <Alert variant="default" className="absolute top-2 left-auto right-2 w-96">
            <CircleDollarSign />
            <AlertTitle> Spending Alert</AlertTitle>
            <AlertDescription>
              Your expenses this month are significantly higher than in previous months. Please
              review your recent transactions.
            </AlertDescription>
            <X
              className="absolute top-1 left-auto right-2 hover:cursor-pointer"
              onPointerDown={() => setShowAlert(false)}
            />
          </Alert>
        )}

        <div className="flex flex-1 flex-col">
          <div className="@container/main flex flex-1 flex-col gap-2">
            <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
              <div className="lg:flex lg:flex-row px-4 lg:px-6">
                <div className="w-full lg:w-2/3">
                  <ChartBarMultiple />
                </div>
                <div className="w-full lg:w-1/3 lg:pl-6 pt-4 lg:pt-0">
                  <ChartPieDonut />
                </div>
              </div>
              <DataTable data={data} />
            </div>
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
