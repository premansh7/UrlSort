const API_BASE = "http://localhost:8080";

const form = document.getElementById("shortenForm");
const input = document.getElementById("urlInput");
const button = document.getElementById("shortenBtn");
const result = document.getElementById("result");
const shortUrl = document.getElementById("shortUrl");
const originalUrl = document.getElementById("originalUrl");
const errorMessage = document.getElementById("errorMessage");
const copyBtn = document.getElementById("copyBtn");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const url = input.value.trim();
  if (!url) return;

  setLoading(true);
  hideError();
  result.classList.remove("visible");

  try {
    const response = await fetch(`${API_BASE}/api/shorten`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        originalUrl: url
      })
    });

    if (!response.ok) {
      let message = "Could not shorten that URL.";
      try {
        const data = await response.json();
        message = data.message || data.error || message;
      } catch (_) {}
      throw new Error(message);
    }

    const data = await response.json();

    shortUrl.href = data.shortUrl;
    shortUrl.textContent = data.shortUrl;
    originalUrl.textContent = data.originalUrl || url;

    result.classList.add("visible");
  } catch (error) {
    showError(
      error.message.includes("Failed to fetch")
        ? "Can't reach urlsort. Make sure the Spring Boot backend is running on port 8080."
        : error.message
    );
  } finally {
    setLoading(false);
  }
});

copyBtn.addEventListener("click", async () => {
  try {
    await navigator.clipboard.writeText(shortUrl.textContent);
    copyBtn.textContent = "COPIED";
    setTimeout(() => copyBtn.textContent = "COPY", 1400);
  } catch (_) {
    copyBtn.textContent = "FAILED";
    setTimeout(() => copyBtn.textContent = "COPY", 1400);
  }
});

function setLoading(loading) {
  button.disabled = loading;
  button.querySelector("span:first-child").textContent =
    loading ? "Shortening..." : "Shorten";
}

function showError(message) {
  errorMessage.textContent = message;
  errorMessage.style.display = "block";
}

function hideError() {
  errorMessage.textContent = "";
  errorMessage.style.display = "none";
}
