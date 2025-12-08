
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    try {
        const body = await req.json();
        const { url } = body;

        if (!url) {
            return NextResponse.json({ status: "DOWN", message: "URL is required" }, { status: 400 });
        }

        // 서버 사이드에서 내부 Docker URL로 요청
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 3000); // 3초 타임아웃

        try {
            const response = await fetch(url, { signal: controller.signal });
            clearTimeout(timeoutId);

            if (response.ok) {
                return NextResponse.json({ status: "UP", responseTime: 0 }); // responseTime은 프론트에서 계산하거나 여기서 측정 가능
            } else {
                return NextResponse.json({ status: "DOWN", statusCode: response.status }, { status: 200 }); // 응답은 왔지만 에러인 경우
            }
        } catch (fetchError) {
            clearTimeout(timeoutId);
            return NextResponse.json({ status: "DOWN", message: "Unreachable" }, { status: 200 });
        }

    } catch (error) {
        return NextResponse.json({ status: "DOWN", error: "Internal Error" }, { status: 500 });
    }
}
