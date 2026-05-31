<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes - Enlatados MG</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

    <div id="layout">
        <aside id="sidebar"></aside>

        <main>
            <div class="page-header">
                <div>
                    <h1>Gestión de Clientes</h1>
                    <p style="color: var(--color-texto-claro);">Administración de clientes (Árbol AVL balanceado por CUI/DPI)</p>
                </div>
                <div style="display: flex; gap: 12px; align-items: center;">
                    <input type="file" id="csv-file" accept=".csv" style="display: none;">
                    <button id="btn-cargar-csv" class="btn-secundario">📁 Cargar CSV</button>
                </div>
            </div>

            <!-- Búsqueda Rápida -->
            <div class="card" style="padding: 1.25rem 2rem;">
                <div style="display: flex; gap: 12px; align-items: flex-end;">
                    <div class="field-group" style="flex-grow: 1; max-width: 400px;">
                        <label for="txt-buscar-cui">Buscar por CUI/DPI</label>
                        <input type="text" id="txt-buscar-cui" placeholder="Ej: 1234567890101">
                    </div>
                    <button id="btn-buscar">🔍 Buscar</button>
                    <button id="btn-limpiar-busqueda" class="btn-secundario" style="display: none;">Limpiar</button>
                </div>
            </div>

            <!-- Formulario de Registro / Edición -->
            <div class="card">
                <h3 class="card-title" id="form-title">👤 Registrar Nuevo Cliente</h3>
                <div class="form-grid">
                    <div class="field-group">
                        <label for="txt-cui">CUI / DPI (Llave AVL)</label>
                        <input type="text" id="txt-cui" placeholder="Ej: 1234567890101">
                    </div>
                    <div class="field-group">
                        <label for="txt-nombre">Nombre</label>
                        <input type="text" id="txt-nombre" placeholder="Ej: María">
                    </div>
                    <div class="field-group">
                        <label for="txt-apellidos">Apellidos</label>
                        <input type="text" id="txt-apellidos" placeholder="Ej: González López">
                    </div>
                    <div class="field-group">
                        <label for="txt-telefono">Teléfono</label>
                        <input type="text" id="txt-telefono" placeholder="Ej: 5555-1234">
                    </div>
                    <div class="field-group">
                        <label for="txt-direccion">Dirección (Opcional)</label>
                        <input type="text" id="txt-direccion" placeholder="Ej: Zona 1, Guatemala">
                    </div>
                </div>
                <div style="display: flex; gap: 12px; justify-content: flex-end; margin-top: 1rem;">
                    <button id="btn-cancelar" class="btn-secundario" style="display: none;">Cancelar</button>
                    <button id="btn-guardar">Registrar Cliente</button>
                </div>
            </div>

            <!-- Listado de Clientes -->
            <div class="card">
                <h3 class="card-title">📋 Listado de Clientes (Ordenado por CUI)</h3>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>CUI / DPI</th>
                                <th>Nombre Completo</th>
                                <th>Teléfono</th>
                                <th>Dirección</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="tabla-clientes-body">
                            <!-- Inyectado dinámicamente -->
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <!-- Modal de errores -->
    <div id="error-dialog" class="dialog-overlay">
        <div class="dialog-content">
            <h3 style="color: var(--color-acento); margin-bottom: 1rem;">⚠️ Errores de Carga Masiva</h3>
            <div id="error-list-content" style="max-height: 250px; overflow-y: auto; font-size: 0.9rem; color: var(--color-texto-claro); margin-bottom: 1.5rem;"></div>
            <div style="display: flex; justify-content: flex-end;">
                <button id="btn-cerrar-dialog">Aceptar</button>
            </div>
        </div>
    </div>

    <script type="module" src="/js/nav.js"></script>
    <script type="module" src="/js/clientes.js"></script>
</body>
</html>
