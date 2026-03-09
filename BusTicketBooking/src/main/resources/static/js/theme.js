document.addEventListener("DOMContentLoaded", () => {
  const toggleBtn = document.getElementById("darkModeToggle");
  const htmlEl = document.documentElement;
  const currentTheme = localStorage.getItem("theme");

  if (currentTheme === "dark") {
    htmlEl.classList.add("dark");
  }

  toggleBtn?.addEventListener("click", () => {
    htmlEl.classList.toggle("dark");
    localStorage.setItem("theme", htmlEl.classList.contains("dark") ? "dark" : "light");
  });
});
