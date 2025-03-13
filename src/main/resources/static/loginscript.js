document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault(); // Evitar que el formulario se envíe de forma tradicional

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("https://localhost:8443/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (response.ok) {
            window.location.href = "/index.html"; // Redirigir al index.html
        } else {
            const errorData = await response.json();
            document.getElementById("errorMessage").textContent = errorData.error || "Usuario o contraseña incorrectos";
            document.getElementById("errorMessage").style.display = "block";
        }
    } catch (error) {
        console.error("Error:", error);
        document.getElementById("errorMessage").textContent = "Error al iniciar sesión";
        document.getElementById("errorMessage").style.display = "block";
    }
});

document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault(); // Evitar que el formulario se envíe de forma tradicional

    const username = document.getElementById("registerUsername").value;
    const password = document.getElementById("registerPassword").value;

    try {
        const response = await fetch("https://localhost:8443/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (response.ok) {
            alert("Usuario registrado con éxito. Por favor, inicia sesión.");
        } else {
            const errorData = await response.json();
            document.getElementById("registerErrorMessage").textContent = errorData.error || "Error al registrar usuario";
            document.getElementById("registerErrorMessage").style.display = "block";
        }
    } catch (error) {
        console.error("Error:", error);
        document.getElementById("registerErrorMessage").textContent = "Error al registrar usuario";
        document.getElementById("registerErrorMessage").style.display = "block";
    }
});