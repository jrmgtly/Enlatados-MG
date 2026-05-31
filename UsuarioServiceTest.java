import { api, showToast } from './api.js';
import { verificarSesion } from './auth.js';

const txtCantidad   = document.getElementById('txt-cantidad');
const btnIngresar   = document.getElementById('btn-ingresar');
const lblStockTotal = document.getElementById('lbl-stock-total');
const tablaBody     = document.getElementById('tabla-cajas-body');

function formatearFecha(fechaStr) {
    if (!fechaStr) return '-';
    return new Date(fechaStr).toLocaleString('es-GT', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false
    });
}

async function cargarAlmacen() {
    const resStock = await api.almacen.obtenerStock();
    if (resStock && resStock.exito) {
        lblStockTotal.textContent = resStock.datos;
    }

    const resCajas = await api.almacen.listarCajas();
    if (resCajas && resCajas.exito) {
        tablaBody.innerHTML = '';
        if (resCajas.datos.length === 0) {
            tablaBody.innerHTML = `<tr><td colspan="3" style="text-align:center;color:var(--color-texto-claro);">El almacén está vacío. Ingrese cajas arriba.</td></tr>`;
            return;
        }
        let pos = 1;
        resCajas.datos.forEach(c => {
            const tr = document.createElement('tr');
            const tope = pos === 1 ? ' <span style="background:var(--color-acento);color:white;padding:2px 6px;border-radius:4px;font-size:0.75rem;font-weight:bold;margin-left:8px;">TOPE</span>' : '';
            tr.innerHTML = `
                <td><strong>${pos}</strong>${tope}</td>
                <td><span style="font-family:monospace;font-size:1.1rem;color:var(--color-primario-oscuro);">#${c.correlativo}</span></td>
                <td>${formatearFecha(c.fechaIngreso)}</td>
            `;
            tablaBody.appendChild(tr);
            pos++;
        });
    } else {
        showToast((resCajas && resCajas.mensaje) || 'Error al cargar almacén', 'error');
    }
}

btnIngresar.addEventListener('click', async () => {
    const cant = parseInt(txtCantidad.value);
    if (isNaN(cant) || cant <= 0) {
        showToast('Ingrese una cantidad válida mayor a cero.', 'error');
        return;
    }
    const res = await api.almacen.agregarCajas(cant);
    if (res && res.exito) {
        showToast(`Se agregaron ${cant} cajas a la pila del almacén.`);
        cargarAlmacen();
    } else {
        showToast((res && res.mensaje) || 'Error al agregar cajas', 'error');
    }
});

document.addEventListener('DOMContentLoaded', async () => {
    await verificarSesion();
    cargarAlmacen();
});
