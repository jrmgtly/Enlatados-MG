/* API Wrapper — Enlatados MG */

// Helper para mostrar notificaciones flotantes (toasts)
export function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    if (!container) {
        const div = document.createElement('div');
        div.id = 'toast-container';
        div.className = 'toast-container';
        document.body.appendChild(div);
    }
    
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span>${type === 'success' ? '✓' : '✗'}</span>
        <div>${message}</div>
    `;
    
    document.getElementById('toast-container').appendChild(toast);
    
    // Auto-eliminar después de 4 segundos
    setTimeout(() => {
        toast.style.animation = 'slideIn 0.3s ease reverse';
        setTimeout(() => {
            toast.remove();
        }, 300);
    }, 4000);
}

// Wrapper de fetch general
async function apiFetch(url, options = {}) {
    if (!options.headers) {
        options.headers = {};
    }
    
    // Si enviamos body como objeto, lo serializamos a JSON y fijamos el header
    if (options.body && typeof options.body === 'object' && !(options.body instanceof String)) {
        options.headers['Content-Type'] = 'application/json;charset=UTF-8';
        options.body = JSON.stringify(options.body);
    }
    
    try {
        const response = await fetch(url, options);
        
        // Si no está autorizado (sesión expirada o sin sesión), redirigir si no estamos ya en login
        if (response.status === 401 && !url.includes('/api/auth/')) {
            const path = window.location.pathname;
            const esPublica = path === '/' || path === '' || path.endsWith('/login.html');
            if (!esPublica) {
                window.location.replace('/login.html');
            }
            return { exito: false, mensaje: 'Sesión no autorizada o expirada.' };
        }
        
        // Si el endpoint retorna texto plano (como DOT en reportes)
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('text/plain')) {
            return await response.text();
        }
        
        const data = await response.json();
        if (!response.ok) {
            return {
                exito: false,
                mensaje: data.mensaje || 'Ocurrió un error en el servidor.'
            };
        }
        return data;
    } catch (error) {
        console.error('Error en API:', error);
        return {
            exito: false,
            mensaje: 'Error de red o servidor no disponible.'
        };
    }
}

// Endpoints
export const api = {
    auth: {
        login: (id, contrasena) => apiFetch('/api/auth/login', {
            method: 'POST',
            body: { id, contrasena }
        }),
        logout: () => apiFetch('/api/auth/logout', { method: 'POST' }),
        perfil: () => apiFetch('/api/auth/perfil')
    },
    usuarios: {
        listar: () => apiFetch('/api/usuarios'),
        crear: (usr) => apiFetch('/api/usuarios', { method: 'POST', body: usr }),
        buscar: (id) => apiFetch(`/api/usuarios/${id}`),
        modificar: (id, usr) => apiFetch(`/api/usuarios/${id}`, { method: 'PUT', body: usr }),
        eliminar: (id) => apiFetch(`/api/usuarios/${id}`, { method: 'DELETE' }),
        cargarCsv: (csvText) => apiFetch('/api/carga/usuarios', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
            body: csvText
        })
    },
    clientes: {
        listar: () => apiFetch('/api/clientes'),
        crear: (cli) => apiFetch('/api/clientes', { method: 'POST', body: cli }),
        buscar: (cui) => apiFetch(`/api/clientes/${cui}`),
        modificar: (cui, cli) => apiFetch(`/api/clientes/${cui}`, { method: 'PUT', body: cli }),
        eliminar: (cui) => apiFetch(`/api/clientes/${cui}`, { method: 'DELETE' }),
        cargarCsv: (csvText) => apiFetch('/api/carga/clientes', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
            body: csvText
        })
    },
    repartidores: {
        listar: () => apiFetch('/api/repartidores'),
        crear: (rep) => apiFetch('/api/repartidores', { method: 'POST', body: rep }),
        buscar: (cui) => apiFetch(`/api/repartidores/${cui}`),
        modificar: (cui, rep) => apiFetch(`/api/repartidores/${cui}`, { method: 'PUT', body: rep }),
        eliminar: (cui) => apiFetch(`/api/repartidores/${cui}`, { method: 'DELETE' }),
        cargarCsv: (csvText) => apiFetch('/api/carga/repartidores', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
            body: csvText
        })
    },
    vehiculos: {
        listar: () => apiFetch('/api/vehiculos'),
        crear: (veh) => apiFetch('/api/vehiculos', { method: 'POST', body: veh }),
        buscar: (placa) => apiFetch(`/api/vehiculos/${placa}`),
        modificar: (placa, veh) => apiFetch(`/api/vehiculos/${placa}`, { method: 'PUT', body: veh }),
        eliminar: (placa) => apiFetch(`/api/vehiculos/${placa}`, { method: 'DELETE' }),
        cargarCsv: (csvText) => apiFetch('/api/carga/vehiculos', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
            body: csvText
        })
    },
    almacen: {
        agregarCajas: (cantidad) => apiFetch(`/api/almacen/cajas?cantidad=${cantidad}`, { method: 'POST' }),
        listarCajas: () => apiFetch('/api/almacen/cajas'),
        obtenerStock: () => apiFetch('/api/almacen/stock')
    },
    pedidos: {
        listar: () => apiFetch('/api/pedidos'),
        crear: (ped) => apiFetch('/api/pedidos', { method: 'POST', body: ped }),
        buscar: (num) => apiFetch(`/api/pedidos/${num}`),
        obtenerCajas: (num) => apiFetch(`/api/pedidos/${num}/cajas`),
        completar: (num) => apiFetch(`/api/pedidos/${num}/completar`, { method: 'PUT' })
    },
    reportes: {
        obtenerDot: (tipo) => apiFetch(`/api/reportes/${tipo}`)
    }
};
