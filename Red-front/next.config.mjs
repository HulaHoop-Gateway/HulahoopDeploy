/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: "/api/:path*",
                destination: "http://red-back:8000/api/:path*",
            },
        ];
    },
};

export default nextConfig;
