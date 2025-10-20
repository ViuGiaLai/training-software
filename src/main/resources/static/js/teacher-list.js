// Function to set select value
function setSelectValue(selectId, value) {
    const select = document.getElementById(selectId);
    if (!select) return;
    for (let i = 0; i < select.options.length; i++) {
        if (select.options[i].value === value) {
            select.selectedIndex = i;
            return;
        }
    }
    // If no match, select first option
    select.selectedIndex = 0;
}

// Initialize when DOM is fully loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('Teacher list script loaded');

    // Function to handle view button click
    function handleViewClick(btn) {
        console.log('View button clicked', btn.dataset);
        const modalElement = document.getElementById('viewTeacherModal');
        if (!modalElement) {
            console.error('View modal not found');
            return;
        }
        
        const modal = new bootstrap.Modal(modalElement);
        
        // Update modal content with teacher data
        const updateField = (id, value) => {
            const el = document.getElementById(id);
            if (el) el.textContent = value || '';
        };
        
        updateField('view-fullname', btn.dataset.fullname);
        updateField('view-username', btn.dataset.username);
        updateField('view-email', btn.dataset.email);
        updateField('view-department', btn.dataset.department);
        updateField('view-position', btn.dataset.position);
        updateField('view-degree', btn.dataset.degree);
        updateField('view-status', btn.dataset.status);
        updateField('view-createdat', btn.dataset.createdat);
            
        // Update avatar if exists
        const avatar = document.getElementById('view-avatar');
        if (avatar) {
            avatar.src = btn.dataset.avatar || '/images/default-avatar.png';
            avatar.alt = btn.dataset.fullname || 'Avatar';
        }
        
        // Show the modal
        modal.show();
    }
    
    // Function to handle edit button click
    function handleEditClick(btn) {
        console.log('Edit button clicked', btn.dataset);
        const form = document.getElementById('editTeacherForm');
        
        if (!form) {
            console.error('Edit form not found');
            return;
        }
        
        // Set form action with teacher ID
        form.action = `/admin/dashboard/users/user-teacher-list/edit`;
        
        // Add hidden input for teacher ID if it doesn't exist
        let idInput = form.querySelector('input[name="id"]');
        if (!idInput) {
            idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            form.prepend(idInput);
        }
        idInput.value = btn.dataset.id || '';
            
        // Fill the form with teacher data
        const setValue = (id, value) => {
            const el = document.getElementById(id);
            if (el) el.value = value || '';
        };
        
        setValue('edit-fullname', btn.dataset.fullname);
        setValue('edit-username', btn.dataset.username);
        setValue('edit-email', btn.dataset.email);
        
        // Set select values
        setSelectValue('edit-department', btn.dataset.department || '');
        setSelectValue('edit-position', btn.dataset.position || '');
        setSelectValue('edit-degree', btn.dataset.degree || '');
        setSelectValue('edit-status', btn.dataset.status || '');
        
        // Set avatar and password
        const avatarInput = document.getElementById('edit-avatar');
        if (avatarInput) {
            avatarInput.value = btn.dataset.avatar || '';
            
            // Update avatar preview if exists
            const avatarPreview = document.getElementById('editAvatarPreview');
            if (avatarPreview) {
                avatarPreview.src = btn.dataset.avatar || '/images/default-avatar.png';
            }
        }
        
        const passwordInput = document.getElementById('edit-password');
        if (passwordInput) {
            passwordInput.value = btn.dataset.password || '';
        }
        
        // Show the modal
        const modalElement = document.getElementById('editTeacherModal');
        if (modalElement) {
            const modal = new bootstrap.Modal(modalElement);
            modal.show();
        }
    }
    
    // Function to handle delete button click
    function handleDeleteClick(btn) {
        console.log('Delete button clicked', btn.dataset);
        const modalElement = document.getElementById('deleteTeacherModal');
        if (!modalElement) {
            console.error('Delete modal not found');
            return;
        }
        
        const modal = new bootstrap.Modal(modalElement);
        
        // Update confirmation message
        const deleteText = document.getElementById('delete-fullname');
        if (deleteText) {
            deleteText.textContent = btn.dataset.fullname || 'giảng viên này';
        }
        
        // Update form action with teacher ID
        const form = document.getElementById('deleteTeacherForm');
        if (form) {
            form.action = `/admin/dashboard/users/user-teacher-list/delete`;
            
            // Add hidden input for teacher ID if it doesn't exist
            let idInput = form.querySelector('input[name="id"]');
            if (!idInput) {
                idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.name = 'id';
                form.prepend(idInput);
            }
            idInput.value = btn.dataset.id || '';
        }
        
        // Show the modal
        modal.show();
    }
    
    // Handle all clicks on the document
    document.addEventListener('click', function(e) {
        // View button click
        if (e.target.closest('.btn-view')) {
            e.preventDefault();
            handleViewClick(e.target.closest('.btn-view'));
        }
        // Edit button click
        else if (e.target.closest('.btn-edit')) {
            e.preventDefault();
            handleEditClick(e.target.closest('.btn-edit'));
        }
        // Delete button click
        else if (e.target.closest('.btn-delete')) {
            e.preventDefault();
            handleDeleteClick(e.target.closest('.btn-delete'));
        }
    });
    
    // Handle image preview for edit form (URL input)
    const editAvatarInput = document.getElementById('edit-avatar');
    if (editAvatarInput) {
        editAvatarInput.addEventListener('input', function(e) {
            const preview = document.getElementById('editAvatarPreview');
            if (preview) {
                preview.src = e.target.value || '/images/default-avatar.png';
            }
        });
    }

    // Handle image preview for add form (URL input)
    const addAvatarInput = document.querySelector('input[name="avatar"]');
    if (addAvatarInput) {
        addAvatarInput.addEventListener('input', function(e) {
            const preview = document.getElementById('addAvatarPreview');
            if (preview) {
                preview.src = e.target.value || '/images/default-avatar.png';
            }
        });
    }
    
    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(function (tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });
});
