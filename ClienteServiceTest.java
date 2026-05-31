/* Estilos Globales — Enlatados MG */
:root {
    --color-primario: #D4763C;        /* Naranja cálido */
    --color-primario-oscuro: #B85A25; /* Naranja hover */
    --color-secundario: #E8A951;       /* Dorado/Ámbar */
    --color-acento: #C94C4C;           /* Terracota */
    --color-fondo: #FFFDF9;            /* Fondo crema claro */
    --color-fondo-card: #FFFFFF;       /* Blanco puro */
    --color-sidebar: #3D2B1F;          /* Marrón café oscuro */
    --color-texto: #3D2B1F;            /* Marrón oscuro */
    --color-texto-claro: #8B7355;      /* Marrón beige */
    --color-exito: #5D8A4C;            /* Verde oliva cálido */
    --color-error: #C94C4C;            /* Terracota */
    --color-borde: #F0E2D3;            /* Borde beige suave */
    --sombra: 0 4px 12px rgba(61, 43, 31, 0.08);
    --sombra-hover: 0 8px 24px rgba(61, 43, 31, 0.12);
    --transicion: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
    --radio: 12px;
}

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: 'Outfit', 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
    background-color: var(--color-fondo);
    color: var(--color-texto);
    line-height: 1.6;
    display: flex;
    min-height: 100vh;
}

/* Tipografía de Google Fonts */
@import url('https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap');

/* Contenedor Principal con Sidebar */
#layout {
    display: flex;
    width: 100%;
}

/* Sidebar */
aside {
    width: 280px;
    background-color: var(--color-sidebar);
    color: #FFF8F0;
    display: flex;
    flex-direction: column;
    padding: 2rem 1.5rem;
    position: fixed;
    height: 100vh;
    left: 0;
    top: 0;
    z-index: 100;
    box-shadow: 4px 0 20px rgba(0,0,0,0.15);
}

.logo-container {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 2.5rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid rgba(240, 226, 211, 0.15);
}

.logo-icon {
    font-size: 2rem;
    background: var(--color-primario);
    padding: 8px;
    border-radius: 10px;
    color: white;
}

.logo-text h2 {
    font-weight: 700;
    font-size: 1.3rem;
    letter-spacing: 0.5px;
    color: #FFFFFF;
}

.logo-text span {
    font-size: 0.75rem;
    color: var(--color-secundario);
    text-transform: uppercase;
    letter-spacing: 1px;
}

nav {
    display: flex;
    flex-direction: column;
    gap: 8px;
    flex-grow: 1;
}

.nav-link {
    display: flex;
    align-items: center;
    gap: 12px;
    color: #EBD9C6;
    text-decoration: none;
    padding: 12px 16px;
    border-radius: 8px;
    font-weight: 500;
    transition: var(--transicion);
}

.nav-link:hover {
    background-color: rgba(212, 118, 60, 0.15);
    color: var(--color-secundario);
    transform: translateX(4px);
}

.nav-link.active {
    background-color: var(--color-primario);
    color: #FFFFFF;
    box-shadow: 0 4px 10px rgba(212, 118, 60, 0.3);
}

.user-profile {
    margin-top: auto;
    padding-top: 1rem;
    border-top: 1px solid rgba(240, 226, 211, 0.15);
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.user-info {
    font-size: 0.85rem;
}

.user-name {
    font-weight: 600;
    color: white;
}

.user-role {
    color: var(--color-secundario);
}

/* Área de contenido principal */
main {
    margin-left: 280px;
    padding: 2.5rem;
    width: calc(100% - 280px);
    display: flex;
    flex-direction: column;
    gap: 2rem;
}

/* Encabezado de página */
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.page-header h1 {
    font-weight: 700;
    font-size: 2rem;
    color: var(--color-texto);
    position: relative;
}

.page-header h1::after {
    content: '';
    display: block;
    width: 60px;
    height: 4px;
    background-color: var(--color-primario);
    margin-top: 6px;
    border-radius: 2px;
}

/* Cards y Contenedores */
.card {
    background-color: var(--color-fondo-card);
    border: 1px solid var(--color-borde);
    border-radius: var(--radio);
    box-shadow: var(--sombra);
    padding: 2rem;
    transition: var(--transicion);
}

.card:hover {
    box-shadow: var(--sombra-hover);
}

.card-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1.5rem;
    border-bottom: 2px solid var(--color-fondo);
    padding-bottom: 0.5rem;
    display: flex;
    align-items: center;
    gap: 8px;
}

/* Grid de Formularios y Columnas */
.form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 1.5rem;
    margin-bottom: 1.5rem;
}

/* Soporte Vaadin en diseño claro */
vaadin-text-field, vaadin-select, vaadin-integer-field, vaadin-password-field {
    width: 100%;
}

vaadin-button {
    cursor: pointer;
}

/* Tablas con diseño personalizado */
.table-container {
    overflow-x: auto;
    border-radius: 8px;
    border: 1px solid var(--color-borde);
    background-color: white;
}

table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
}

th, td {
    padding: 14px 18px;
    border-bottom: 1px solid var(--color-borde);
}

th {
    background-color: #FFF4E8;
    color: var(--color-primario-oscuro);
    font-weight: 600;
}

tr:hover {
    background-color: #FFFDFB;
}

/* Estilos específicos de Dashboard */
.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 1.5rem;
}

.stat-card {
    background: white;
    border: 1px solid var(--color-borde);
    border-radius: var(--radio);
    padding: 1.5rem;
    display: flex;
    align-items: center;
    gap: 16px;
    box-shadow: var(--sombra);
    transition: var(--transicion);
}

.stat-card:hover {
    transform: translateY(-4px);
    box-shadow: var(--sombra-hover);
}

.stat-icon {
    font-size: 2.2rem;
    color: var(--color-primario);
    background-color: #FFF2E6;
    padding: 12px;
    border-radius: 12px;
}

.stat-info h3 {
    font-size: 0.85rem;
    color: var(--color-texto-claro);
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.stat-info p {
    font-size: 1.8rem;
    font-weight: 700;
    color: var(--color-texto);
}

/* Login Page Layout */
.login-body {
    background-color: #FFF8F2;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    width: 100vw;
}

.login-card {
    max-width: 420px;
    width: 100%;
    padding: 2.5rem;
    border-radius: var(--radio);
    background: white;
    box-shadow: 0 10px 30px rgba(61, 43, 31, 0.08);
    border: 1px solid var(--color-borde);
    text-align: center;
}

.login-logo {
    font-size: 3.5rem;
    color: var(--color-primario);
    margin-bottom: 1rem;
}

.login-card h1 {
    font-size: 1.6rem;
    margin-bottom: 6px;
}

.login-card p {
    color: var(--color-texto-claro);
    font-size: 0.9rem;
    margin-bottom: 2rem;
}

.login-form {
    display: flex;
    flex-direction: column;
    gap: 1.25rem;
    text-align: left;
}

/* Notificación Toast */
.toast-container {
    position: fixed;
    bottom: 24px;
    right: 24px;
    display: flex;
    flex-direction: column;
    gap: 8px;
    z-index: 1000;
}

.toast {
    background-color: white;
    border-left: 5px solid var(--color-primario);
    padding: 16px 24px;
    border-radius: 8px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    font-weight: 500;
    display: flex;
    align-items: center;
    gap: 12px;
    animation: slideIn 0.3s ease;
}

.toast.success {
    border-left-color: var(--color-exito);
}

.toast.error {
    border-left-color: var(--color-error);
}

@keyframes slideIn {
    from { transform: translateX(100%); opacity: 0; }
    to { transform: translateX(0); opacity: 1; }
}

/* Contenedor del Reporte SVG */
.report-viewer {
    border: 1px dashed var(--color-borde);
    background-color: #FFFDFB;
    border-radius: 8px;
    min-height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    overflow: auto;
}

.report-viewer svg {
    max-width: 100%;
    height: auto;
}

/* Modal y diálogos */
.dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(61, 43, 31, 0.4);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 500;
    opacity: 0;
    pointer-events: none;
    transition: opacity 0.25s ease;
}

.dialog-overlay.active {
    opacity: 1;
    pointer-events: auto;
}

.dialog-content {
    background-color: white;
    border-radius: var(--radio);
    padding: 2rem;
    max-width: 500px;
    width: 90%;
    box-shadow: 0 10px 25px rgba(0,0,0,0.1);
    transform: translateY(20px);
    transition: transform 0.25s ease;
}

.dialog-overlay.active .dialog-content {
    transform: translateY(0);
}

/* ====== Inputs y Botones Nativos ====== */
.field-group {
    display: flex;
    flex-direction: column;
    gap: 6px;
    text-align: left;
}

.field-group label {
    font-size: 0.85rem;
    font-weight: 600;
    color: var(--color-texto);
    letter-spacing: 0.3px;
}

input[type="text"],
input[type="number"],
input[type="password"],
input[type="email"],
select,
textarea {
    width: 100%;
    padding: 11px 14px;
    border: 1.5px solid var(--color-borde);
    border-radius: 8px;
    font-size: 0.95rem;
    font-family: inherit;
    color: var(--color-texto);
    background-color: #FDFAF7;
    transition: border-color 0.2s, box-shadow 0.2s;
    outline: none;
}

input[type="text"]:focus,
input[type="number"]:focus,
input[type="password"]:focus,
input[type="email"]:focus,
select:focus,
textarea:focus {
    border-color: var(--color-primario);
    box-shadow: 0 0 0 3px rgba(212, 118, 60, 0.15);
    background-color: #FFFFFF;
}

input::placeholder {
    color: #C2A98A;
}

button,
.btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    padding: 11px 22px;
    border: none;
    border-radius: 8px;
    font-size: 0.95rem;
    font-family: inherit;
    font-weight: 600;
    cursor: pointer;
    transition: var(--transicion);
    background-color: var(--color-primario);
    color: white;
}

button:hover,
.btn:hover {
    background-color: var(--color-primario-oscuro);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(212, 118, 60, 0.3);
}

button:active,
.btn:active {
    transform: translateY(0);
}

button:disabled,
.btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
    box-shadow: none;
}

.btn-secundario {
    background-color: transparent;
    color: var(--color-primario);
    border: 1.5px solid var(--color-primario);
}

.btn-secundario:hover {
    background-color: rgba(212, 118, 60, 0.08);
    transform: translateY(-1px);
    box-shadow: none;
}

.btn-peligro {
    background-color: var(--color-error);
}

.btn-peligro:hover {
    background-color: #A83A3A;
    box-shadow: 0 4px 12px rgba(201, 76, 76, 0.3);
}

select {
    appearance: none;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23D4763C' stroke-width='2'%3E%3Cpolyline points='6 9 12 15 18 9'%3E%3C/polyline%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 12px center;
    padding-right: 40px;
}

/* ====== Login Específico ====== */
.login-subtitle {
    color: var(--color-texto-claro);
    font-size: 0.9rem;
    margin-bottom: 1.75rem;
}

#btn-login {
    width: 100%;
    padding: 13px;
    font-size: 1rem;
    margin-top: 0.5rem;
    border-radius: 8px;
    letter-spacing: 0.3px;
}

.login-error {
    margin-top: 1rem;
    padding: 10px 14px;
    background-color: #FDEAEA;
    border-left: 4px solid var(--color-error);
    border-radius: 6px;
    color: var(--color-error);
    font-size: 0.875rem;
    text-align: left;
}
