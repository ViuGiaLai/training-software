/**
 * Admin Dashboard Script
 * Handles navigation, dropdowns, and dynamic content loading
 */

// Dropdown toggle logic - only one dropdown open at a time
function toggleDropdown(event, id) {
    event.preventDefault();
    event.stopPropagation();
    
    const currentDropdown = document.getElementById(id);
    
    // Close all dropdowns first
    document.querySelectorAll('.submenu').forEach(el => {
        if (el.id !== id) {
            el.style.display = 'none';
            const parent = el.closest('.has-dropdown');
            if (parent) parent.classList.remove('active');
        }
    });
    
    // Toggle current dropdown
    if (currentDropdown) {
        const isHidden = currentDropdown.style.display === 'none' || currentDropdown.style.display === '';
        currentDropdown.style.display = isHidden ? 'block' : 'none';
        
        // Update active state for parent
        const parent = currentDropdown.closest('.has-dropdown');
        if (parent) {
            parent.classList.toggle('active', isHidden);
            
            // Toggle dropdown icon
            const icon = parent.querySelector('.fa-angle-down, .fa-angle-up');
            if (icon) {
                icon.classList.toggle('fa-angle-down', !isHidden);
                icon.classList.toggle('fa-angle-up', isHidden);
            }
        }
    }
}

// Update active menu
function updateActiveMenu(activeKey) {
    if (!activeKey) return;
    
    // Remove all active classes
    document.querySelectorAll('.sidebar-menu a, .dashboard a').forEach(link => {
        link.classList.remove('active');
        const parent = link.parentElement;
        if (parent) {
            parent.classList.remove('active');
            // Close all submenus
            const submenu = parent.querySelector('.submenu');
            if (submenu) {
                submenu.style.display = 'none';
            }
            // Reset dropdown icons
            const icon = parent.querySelector('.fa-angle-down, .fa-angle-up');
            if (icon) {
                icon.classList.add('fa-angle-down');
                icon.classList.remove('fa-angle-up');
            }
        }
    });
    
    // Add active class to selected menu
    const activeLink = document.querySelector(`a[href="#${activeKey}"], a[href$="${activeKey}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
        
        // If has parent dropdown, open it
        const dropdown = activeLink.closest('.has-dropdown');
        if (dropdown) {
            const submenu = dropdown.querySelector('.submenu');
            if (submenu) {
                submenu.style.display = 'block';
                dropdown.classList.add('active');
                // Update dropdown icon
                const icon = dropdown.querySelector('.fa-angle-down, .fa-angle-up');
                if (icon) {
                    icon.classList.remove('fa-angle-down');
                    icon.classList.add('fa-angle-up');
                }
            }
        }
    }
}

// Map page keys to fragment endpoints
const pageMap = {
    'dashboard': '/admin/dashboard/dashboard/fragment',
    'user-list': '/admin/dashboard/user-list/fragment',
    'user-teacher-list': '/admin/dashboard/users/user-teacher-list?fragment=fragment',
    'user-student-list': '/admin/dashboard/users/user-student-list/fragment',
    'course-management': '/admin/dashboard/course-management',
    'exam-management': '/admin/dashboard/exam-management/fragment',
    'schedule-management': '/admin/dashboard/schedule-management/fragment',
    'classroom': '/admin/dashboard/classroom',
    'class-assignment': '/admin/dashboard/class-assignment/fragment'
};

// Get key from href
function getPageKeyFromHref(href) {
    if (!href) return 'dashboard';
    
    // Remove # if present
    href = href.replace(/^#/, '');
    
    // Remove query string and fragment
    href = href.split('?')[0];
    
    // Get last part of URL
    const parts = href.split('/').filter(Boolean);
    let last = parts[parts.length - 1];
    
    // If it's a fragment, get the previous part
    if (last === 'fragment' && parts.length > 1) {
        return parts[parts.length - 2];
    }
    
    return last || 'dashboard';
}

// Show error message
function showError(message) {
    console.error(message);
    // Optionally show error message in UI
    const errorDiv = document.getElementById('error-message');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => { errorDiv.style.display = 'none'; }, 5000);
    }
}

// Load fragment vào mainContent
async function loadFragment(pageKey) {
    if (!pageKey || !pageMap[pageKey]) {
        pageKey = 'dashboard';
    }
    
    const url = pageMap[pageKey];
    if (!url) {
        showError('Invalid path');
        return;
    }
    
    // Show loading
    const mainContent = document.getElementById('mainContent');
    if (mainContent) {
        mainContent.innerHTML = '<div class="text-center p-5"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Loading...</span></div></div>';
    }
    
    // Toggle CSS based on page
    const teacherListCss = document.getElementById('teacher-list-css');
    if (teacherListCss) {
        teacherListCss.disabled = pageKey !== 'user-teacher-list';
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Lỗi tải nội dung: ${response.status} ${response.statusText}`);
        }
        
        const html = await response.text();
        
        if (mainContent) {
            mainContent.innerHTML = html;
            // Cập nhật active menu
            updateActiveMenu(pageKey);
            
            // Khởi tạo lại tooltip
            const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            tooltipTriggerList.forEach(tooltipTriggerEl => {
                new bootstrap.Tooltip(tooltipTriggerEl);
            });
            
            // Kích hoạt các thành phần tương tác
            initializeInteractiveElements();
        }
    } catch (error) {
        console.error('Lỗi khi tải nội dung:', error);
        showError('Đã xảy ra lỗi khi tải nội dung. Vui lòng thử lại.');
        
        // Fallback to dashboard on error
        if (pageKey !== 'dashboard') {
            loadFragment('dashboard');
        }
    }
}

// Khởi tạo các thành phần tương tác
function initializeInteractiveElements() {
    // Khởi tạo các bảng dữ liệu
    const tables = document.querySelectorAll('table[data-datatable]');
    tables.forEach(table => {
        if ($.fn.DataTable.isDataTable(table)) {
            $(table).DataTable().destroy();
        }
        $(table).DataTable({
            responsive: true,
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/vi.json'
            }
        });
    });
    
    // Khởi tạo các form validation
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}

// Handle menu click events
function handleMenuClick(e) {
    const link = e.currentTarget;
    const href = link.getAttribute('href');
    
    // Only process links starting with # or /admin
    if (href && (href.startsWith('#') || href.startsWith('/admin'))) {
        const pageKey = href.startsWith('#') ? href.substring(1) : getPageKeyFromHref(href);
        if (pageKey) {
            // Update URL hash
            window.location.hash = pageKey;
            loadFragment(pageKey);
            e.preventDefault();
            return false;
        }
    }
}

// Load fragment based on hash when page loads
function loadFromHash() {
    const hash = window.location.hash;
    const pageKey = hash ? hash.replace('#', '') : 'dashboard';
    loadFragment(pageKey);
}

// Close dropdowns when clicking outside
function setupClickOutside() {
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.has-dropdown')) {
            document.querySelectorAll('.submenu').forEach(el => {
                el.style.display = 'none';
                const parent = el.closest('.has-dropdown');
                if (parent) {
                    parent.classList.remove('active');
                    // Reset dropdown icon
                    const icon = parent.querySelector('.fa-angle-up');
                    if (icon) {
                        icon.classList.remove('fa-angle-up');
                        icon.classList.add('fa-angle-down');
                    }
                }
            });
        }
    });
}

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    // Handle all sidebar and dashboard links
    document.querySelectorAll('.sidebar-menu a, .dashboard a').forEach(link => {
        link.addEventListener('click', handleMenuClick);
    });
    
    // Setup click outside to close dropdowns
    setupClickOutside();
    
    // Initial load
    loadFromHash();
    
    // Handle back/forward navigation
    window.addEventListener('popstate', loadFromHash);
    
    // Initialize interactive elements
    initializeInteractiveElements();
});
