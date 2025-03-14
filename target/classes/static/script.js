const API_URL = "https://localhost:8443/api/properties";

// Cargar propiedades al iniciar
document.addEventListener("DOMContentLoaded", () => {
    loadProperties();
    setupForm();
    setupSearch();
});

// Función para cargar propiedades
async function loadProperties() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Error loading properties");
        const data = await response.json(); // Respuesta completa de la API
        const properties = data.content; // Accede al array de propiedades dentro de "content"
        console.log(properties); // Verifica el array en la consola
        const tbody = document.querySelector("#propertyTable tbody");
        tbody.innerHTML = properties.length === 0
            ? `<tr><td colspan="6" class="empty-message">No properties found.</td></tr>`
            : properties.map(property => `
                <tr>
                    <td>${property.id}</td>
                    <td>${property.address}</td>
                    <td>${property.price}</td>
                    <td>${property.size}</td>
                    <td>${property.description}</td>
                    <td>
                        <button class="edit" onclick="editProperty(${property.id})">Edit</button>
                        <button class="delete" onclick="deleteProperty(${property.id})">Delete</button>
                    </td>
                </tr>
            `).join("");
    } catch (error) {
        console.error("Error:", error);
        alert("Error loading properties.");
    }
}

// Función para configurar el formulario de agregar propiedades
function setupForm() {
    const propertyForm = document.getElementById("propertyForm");
    propertyForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const property = {
            id: document.getElementById("propertyId").value || null,
            address: document.getElementById("location").value,
            price: parseFloat(document.getElementById("price").value),
            size: parseFloat(document.getElementById("size").value),
            description: document.getElementById("details").value
        };

        const method = property.id ? "PUT" : "POST";
        const url = property.id ? `${API_URL}/${property.id}` : API_URL;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(property)
            });

            if (!response.ok) throw new Error("Error saving property");

            alert(property.id ? "Property updated successfully." : "Property created successfully.");
            document.getElementById("propertyForm").reset();
            loadProperties(); // Recargar la lista de propiedades
        } catch (error) {
            console.error("Error:", error);
            alert("Error saving property.");
        }
    });
}

// Función para editar una propiedad
async function editProperty(id) {
    try {
        console.log("Editing property with ID:", id);
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Error loading property");
        const property = await response.json();
        console.log("Property data:", property);

        // Mostrar el formulario de edición
        document.getElementById("editFormSection").style.display = "block";

        // Cargar los datos de la propiedad en el formulario
        document.getElementById("editPropertyId").value = property.id;
        document.getElementById("editLocation").value = property.address;
        document.getElementById("editPrice").value = property.price;
        document.getElementById("editSize").value = property.size;
        document.getElementById("editDetails").value = property.description;
    } catch (error) {
        console.error("Error:", error);
        alert("Error loading property.");
    }
}

// Función para cancelar la edición
function cancelEdit() {
    // Ocultar el formulario de edición
    document.getElementById("editFormSection").style.display = "none";

    // Limpiar los campos del formulario
    document.getElementById("editForm").reset();
}

// Configurar el evento submit del formulario de edición
document.getElementById("editForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const property = {
        id: document.getElementById("editPropertyId").value,
        address: document.getElementById("editLocation").value,
        price: parseFloat(document.getElementById("editPrice").value),
        size: parseFloat(document.getElementById("editSize").value),
        description: document.getElementById("editDetails").value
    };

    try {
        const response = await fetch(`${API_URL}/${property.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(property)
        });

        if (!response.ok) throw new Error("Error updating property");

        alert("Property updated successfully.");

        // Ocultar el formulario de edición
        document.getElementById("editFormSection").style.display = "none";

        // Limpiar el formulario
        document.getElementById("editForm").reset();

        // Recargar la lista de propiedades
        loadProperties();
    } catch (error) {
        console.error("Error:", error);
        alert("Error updating property.");
    }
});

// Función para eliminar una propiedad
async function deleteProperty(id) {
    if (confirm("Are you sure you want to delete this property?")) {
        try {
            const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
            if (!response.ok) throw new Error("Error deleting property");
            loadProperties(); // Recargar la lista de propiedades
            alert("Property deleted successfully.");
        } catch (error) {
            console.error("Error:", error);
            alert("Error deleting property.");
        }
    }
}

// Función para configurar la búsqueda
function setupSearch() {
    const searchForm = document.getElementById("searchForm");
    searchForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const location = document.getElementById("searchLocation").value;
        const minPrice = document.getElementById("searchMinPrice").value;
        const maxPrice = document.getElementById("searchMaxPrice").value;
        const minSize = document.getElementById("searchMinSize").value;
        const maxSize = document.getElementById("searchMaxSize").value;

        try {
            const response = await fetch(`${API_URL}/search?location=${location}&minPrice=${minPrice}&maxPrice=${maxPrice}&minSize=${minSize}&maxSize=${maxSize}`);
            if (!response.ok) throw new Error("Error searching properties");
            const data = await response.json();
            const properties = data.content; // Accede al array de propiedades dentro de "content"
            const tbody = document.querySelector("#propertyTable tbody");
            tbody.innerHTML = properties.length === 0
                ? `<tr><td colspan="6" class="empty-message">No properties found.</td></tr>`
                : properties.map(property => `
                    <tr>
                        <td>${property.id}</td>
                        <td>${property.address}</td>
                        <td>${property.price}</td>
                        <td>${property.size}</td>
                        <td>${property.description}</td>
                        <td>
                            <button class="edit" onclick="editProperty(${property.id})">Edit</button>
                            <button class="delete" onclick="deleteProperty(${property.id})">Delete</button>
                        </td>
                    </tr>
                `).join("");
        } catch (error) {
            console.error("Error:", error);
            alert("Error searching properties.");
        }
    });
}