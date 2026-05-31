<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Almacén - Enlatados MG</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

    <div id="layout">
        <aside id="sidebar"></aside>

        <main>
            <div class="page-header">
                <div>
                    <h1>Control de Almacén</h1>
                    <p style="color: var(--color-texto-claro);">Gestión de inventario de cajas enlatadas (Pila LIFO)</p>
                </div>
            </div>

            <!-- Resumen e ingreso de stock -->
            <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 2rem;">
                <!-- Agregar stock -->
                <div class="card">
                    <h3 class="card-title">📥 Ingresar Cajas</h3>
                    <div class="field-group" style="margin-bottom: 1.5rem;">
                        <label for="txt-cantidad">Cantidad de cajas a ingresar</label>
                        <input type="number" id="txt-cantidad" value="10" min="1" placeholder="Ej: 50">
                    </div>
                    <button id="btn-ingresar" style="width: 100%;">
                        Ingresar Cajas a la Pila
                    </button>
                </div>

                <!-- Stock actual -->
                <div class="card" style="display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
                    <span style="font-size: 4rem; margin-bottom: 10px;">📦</span>
                    <h2 style="font-size: 2.2rem; font-weight: 700; color: var(--color-primario);" id="lbl-stock-total">-</h2>
                    <p style="color: var(--color-texto-claro); font-weight: 500; text-transform: uppercase; font-size: 0.85rem; letter-spacing: 1px;">Cajas disponibles en la pila (Stock)</p>
                </div>
            </div>

            <!-- Listado de la Pila de Cajas -->
            <div class="card">
                <h3 class="card-title">📋 Cajas en Almacén (Tope de la Pila arriba)</h3>
                <div class="table-container" style="max-height: 400px; overflow-y: auto;">
                    <table>
                        <thead>
                            <tr>
                                <th>Posición en Pila</th>
                                <th>Correlativo (ID de Caja)</th>
                                <th>Fecha y Hora de Ingreso</th>
                            </tr>
                        </thead>
                        <tbody id="tabla-cajas-body">
                            <!-- Inyectado dinámicamente -->
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <script type="module" src="/js/nav.js"></script>
    <script type="module" src="/js/almacen.js"></script>
</body>
</html>
