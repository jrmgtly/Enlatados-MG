<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Enlatados MG</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

    <div id="layout">
        <!-- El sidebar se inyectará dinámicamente -->
        <aside id="sidebar"></aside>

        <main>
            <div class="page-header">
                <div>
                    <h1>Dashboard</h1>
                    <p style="color: var(--color-texto-claro);">Resumen operativo del sistema</p>
                </div>
            </div>

            <!-- Panel de Estadísticas -->
            <div class="stats-grid">
                <div class="stat-card">
                    <span class="stat-icon">📦</span>
                    <div class="stat-info">
                        <h3>Stock Almacén</h3>
                        <p id="stat-almacen">-</p>
                    </div>
                </div>
                <div class="stat-card">
                    <span class="stat-icon">👤</span>
                    <div class="stat-info">
                        <h3>Clientes (AVL)</h3>
                        <p id="stat-clientes">-</p>
                    </div>
                </div>
                <div class="stat-card">
                    <span class="stat-icon">🚴</span>
                    <div class="stat-info">
                        <h3>Repartidores</h3>
                        <p id="stat-repartidores">-</p>
                    </div>
                </div>
                <div class="stat-card">
                    <span class="stat-icon">🚚</span>
                    <div class="stat-info">
                        <h3>Vehículos</h3>
                        <p id="stat-vehiculos">-</p>
                    </div>
                </div>
                <div class="stat-card">
                    <span class="stat-icon">📝</span>
                    <div class="stat-info">
                        <h3>Pedidos</h3>
                        <p id="stat-pedidos">-</p>
                    </div>
                </div>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 1rem;">
                <!-- Accesos rápidos -->
                <div class="card">
                    <h3 class="card-title">🚀 Acceso Rápido</h3>
                    <div style="display: flex; flex-direction: column; gap: 12px; margin-top: 1rem;">
                        <button onclick="window.location.href='/pedidos.html'">
                            + Crear Nuevo Pedido
                        </button>
                        <button class="btn-secundario" onclick="window.location.href='/almacen.html'">
                            📥 Cargar Cajas en Almacén
                        </button>
                        <button class="btn-secundario" onclick="window.location.href='/reportes.html'">
                            👁️ Ver Estructuras de Datos
                        </button>
                    </div>
                </div>

                <!-- Info operativa -->
                <div class="card">
                    <h3 class="card-title">ℹ️ Información del Sistema</h3>
                    <p style="margin-bottom: 12px;">Este sistema gestiona la cadena de suministro utilizando estructuras de datos cargadas en memoria RAM:</p>
                    <ul style="margin-left: 20px; color: var(--color-texto-claro); display: flex; flex-direction: column; gap: 8px;">
                        <li><strong>Usuarios:</strong> Almacenados en una Lista Enlazada.</li>
                        <li><strong>Almacén:</strong> Cajas controladas en una Pila (LIFO).</li>
                        <li><strong>Clientes:</strong> Organizados de forma óptima en un Árbol AVL.</li>
                        <li><strong>Repartidores/Vehículos:</strong> Administrados en colas FIFO.</li>
                        <li><strong>Pedidos:</strong> Almacenados en una Lista Enlazada.</li>
                    </ul>
                </div>
            </div>
        </main>
    </div>

    <script type="module" src="/js/nav.js"></script>

    <script type="module">
        import { api } from '/js/api.js';
        import { verificarSesion } from '/js/auth.js';

        async function cargarStats() {
            const resAlmacen = await api.almacen.obtenerStock();
            if (resAlmacen && resAlmacen.exito) {
                document.getElementById('stat-almacen').textContent = resAlmacen.datos;
            }
            const resClientes = await api.clientes.listar();
            if (resClientes && resClientes.exito) {
                document.getElementById('stat-clientes').textContent = resClientes.datos.length;
            }
            const resRepartidores = await api.repartidores.listar();
            if (resRepartidores && resRepartidores.exito) {
                document.getElementById('stat-repartidores').textContent = resRepartidores.datos.length;
            }
            const resVehiculos = await api.vehiculos.listar();
            if (resVehiculos && resVehiculos.exito) {
                document.getElementById('stat-vehiculos').textContent = resVehiculos.datos.length;
            }
            const resPedidos = await api.pedidos.listar();
            if (resPedidos && resPedidos.exito) {
                document.getElementById('stat-pedidos').textContent = resPedidos.datos.length;
            }
        }

        document.addEventListener('DOMContentLoaded', async () => {
            await verificarSesion();
            cargarStats();
        });
    </script>
</body>
</html>
