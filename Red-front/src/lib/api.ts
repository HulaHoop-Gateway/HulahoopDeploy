import axiosAdmin from "@/api/axiosAdmin";

export interface DashboardData {
    totalMembers: number;
    totalMerchants: number;
    totalApiRequests: number;
    totalTransactions: number;
    dailyTransactions: { date: string; amount: number }[];
    monthlyTransactions: { month: string; amount: number }[];
    categoryRatio: { name: string; value: number }[];
}

export const fetchDashboardData = async (): Promise<DashboardData> => {
    // axiosAdmin을 사용하여 baseURL, interceptors(토큰) 자동 적용
    // Red-back Controller: @RequestMapping("/api/v1/dashboard")
    const response = await axiosAdmin.get("/api/v1/dashboard");
    return response.data;
};
