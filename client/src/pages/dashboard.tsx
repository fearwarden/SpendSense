import { AppSidebar } from "@/components/app-sidebar";
import { ChartBarMultiple } from "@/components/chart-area-interactive";
import { ChartPieDonut } from "@/components/chart-pie-donut";
import { DataTable } from "@/components/data-table";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import data from "./data.json";

export default function Page() {
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
