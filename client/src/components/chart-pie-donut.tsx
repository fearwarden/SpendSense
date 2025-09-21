"use client";

import axios from "axios";
import { TrendingUp } from "lucide-react";
import { Pie, PieChart } from "recharts";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
  type ChartConfig,
} from "@/components/ui/chart";
import { useEffect, useState } from "react";

export const description = "A donut chart";

const chartData = [
  { browser: "ESSENTIALS", visitors: 275, fill: "var(--color-chrome)" },
  { browser: "TRANSPORTATION", visitors: 200, fill: "var(--color-safari)" },
  { browser: "LEISURE", visitors: 187, fill: "var(--color-firefox)" },
  { browser: "TRAVEL", visitors: 173, fill: "var(--color-edge)" },
  { browser: "SUBSCRIPTIONS", visitors: 90, fill: "var(--color-other)" },
  { browser: "MISC", visitors: 90, fill: "var(--color-other)" },
];

const chartConfig = {
  visitors: {
    label: "Visitors",
  },
  chrome: {
    label: "Chrome",
    color: "var(--chart-1)",
  },
  safari: {
    label: "Safari",
    color: "var(--chart-2)",
  },
  firefox: {
    label: "Firefox",
    color: "var(--chart-3)",
  },
  edge: {
    label: "Edge",
    color: "var(--chart-4)",
  },
  other: {
    label: "Other",
    color: "var(--chart-5)",
  },
} satisfies ChartConfig;

export function ChartPieDonut() {
  const [categories, setCategories] = useState<
    {
      browser: string;
      visitors: unknown;
      fill: string;
    }[]
  >([]);

  const getRandomColor = () => {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return [r, g, b];
  };

  const getData = async () => {
    setCategories([]);
    const resp = await axios.get("/backend/api/transaction/analytics/categories");
    const data = resp.data;
    const categoriesObj = data.categories;

    const categoriesList = [];

    for (const category of Object.keys(categoriesObj)) {
      const color = getRandomColor();
      categoriesList.push({
        browser: category,
        visitors: categoriesObj[category],
        fill: `rgb(${color[0]}, ${color[1]}, ${color[2]})`,
      });
    }

    setCategories(categoriesList);
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <Card className="flex flex-col">
      <CardHeader className="items-center pb-0">
        <CardTitle>Overview</CardTitle>
        <CardDescription>August 2025</CardDescription>
      </CardHeader>
      <CardContent className="flex-1 pb-0">
        <ChartContainer config={chartConfig} className="mx-auto aspect-square max-h-[250px]">
          <PieChart>
            <ChartTooltip cursor={false} content={<ChartTooltipContent hideLabel />} />
            <Pie data={categories} dataKey="visitors" nameKey="browser" innerRadius={60} />
          </PieChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col gap-2 text-sm">
        <div className="flex items-center gap-2 leading-none font-medium">
          Trending up by 5.2% this month <TrendingUp className="h-4 w-4" />
        </div>
        <div className="text-muted-foreground leading-none">
          Showing total costs for the last 6 months
        </div>
      </CardFooter>
    </Card>
  );
}
