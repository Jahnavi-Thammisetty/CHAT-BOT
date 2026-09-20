const BASE_URL = "http://localhost:8202";

export const apiFetch = async (url, options = {}) => {

    let accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    // 1. Call original API with access token
    let response = await fetch(`${BASE_URL}${url}`, {
        ...options,
        headers: {
            ...options.headers,
            Authorization: `Bearer ${accessToken}`
        }
    });

    // 2. Access token is still valid
    if (response.status !== 401) {
        return response;
    }

    // 3. No refresh token
    if (!refreshToken) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        window.location.href = "/login";
        return response;
    }

    // 4. Refresh access token
    const refreshResponse = await fetch(
        `${BASE_URL}/v1/refresh`,
        {
            method: "POST",
            headers: {
                Authorization: `Bearer ${refreshToken}`
            }
        }
    );

    // 5. Refresh token expired/invalid
    if (!refreshResponse.ok) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        window.location.href = "/login";
        return refreshResponse;
    }

    // 6. Get new access token
    const refreshData = await refreshResponse.json();

    const newAccessToken = refreshData.accessToken;

    // 7. Save new access token
    localStorage.setItem("accessToken", newAccessToken);

    // 8. Retry original API
    response = await fetch(`${BASE_URL}${url}`, {
        ...options,
        headers: {
            ...options.headers,
            Authorization: `Bearer ${newAccessToken}`
        }
    });

    return response;
};