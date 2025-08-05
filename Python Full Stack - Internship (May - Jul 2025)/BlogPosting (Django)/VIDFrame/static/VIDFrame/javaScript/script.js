function toggleEye(passwordInputEle, eyeIconEle){
    if(passwordInputEle !== undefined && eyeIconEle !== undefined){
        $(eyeIconEle).parent().on('mousedown', function() {
            if ($(passwordInputEle).attr('type') === 'password') {
                $(passwordInputEle).attr('type', 'text'); 
                $(eyeIconEle).removeClass('fa-eye-slash').addClass('fa-eye');
            }
        });
        $(eyeIconEle).parent().on('mouseup mouseleave', function(e) {
            if ($(passwordInputEle).attr('type') === 'text') {
                $(passwordInputEle).attr('type', 'password'); 
                $(eyeIconEle).removeClass('fa-eye').addClass('fa-eye-slash');
            }
        });
    }
}

function toggleEdit(usernameEle, firstnameEle, lastnameEle, emailEle, contactEle){
    $(usernameEle).siblings('span').on('click', function(){
        toggleDisabled(usernameEle, $(this).children('i'));
    });
    $(firstnameEle).siblings('span').on('click', function(){
        toggleDisabled(firstnameEle, $(this).children('i'));
    });
    $(lastnameEle).siblings('span').on('click', function(){
        toggleDisabled(lastnameEle, $(this).children('i'));
    });
    $(emailEle).siblings('span').on('click', function(){
        toggleDisabled(emailEle, $(this).children('i'));
    });
    $(contactEle).siblings('span').on('click', function(){
        toggleDisabled(contactEle, $(this).children('i'));
    });
}

function toggleDisabled(element, penIconEle) {
    element.prop('disabled', !element.prop('disabled'));
    $(penIconEle).toggleClass('fa-times-circle fa-pencil')
}

function mediaUploadSection(event){
    const createMediaBtn = $('#createMediaBtn');
    const mediaUploadModalContainer = $('#mediaUploadModalContainer'); // The placeholder for injected HTML

    $(createMediaBtn).on('click', function(event) {
        event.preventDefault();
        $.ajax({
            url: "/mediaUpload",
            method: 'GET',
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            },
            success: function(html) {
                mediaUploadModalContainer.html(html); 
                console.log(html)
                //mediaUploadModalContainer.childNodes[0].innerHTML
                showPopup(mediaUploadModalContainer); 

                // --- IMPORTANT: Attach event listeners to the NEWLY LOADED elements ---
                mediaUploadModel($(mediaUploadModalContainer));
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error loading popup content:', errorThrown);
                mediaUploadModalContainer.html(`<p class="text-danger">Failed to load content: ${errorThrown}</p>`);
                showPopup(mediaUploadModalContainer);
            }
        });
    });
}


    function showPopup(mediaUploadModalContainer) {
        const popupOverlayElement = $(mediaUploadModalContainer).find('.popup-overlay');
        if (popupOverlayElement) {
            popupOverlayElement.css('display', 'flex'); 
        }
    }

    function hidePopup(mediaUploadModalContainer) {
        const popupOverlayElement = $(mediaUploadModalContainer).find('.popup-overlay');
        if (popupOverlayElement) {
            popupOverlayElement.css('display', 'none'); 
            mediaUploadModalContainer.empty(); 
        }
    }

function mediaUploadModel(mediaUploadModalContainer){
    const mediaUploadForm = mediaUploadModalContainer.find('form');

    const closeButton = mediaUploadModalContainer.find('.popup-close-button');

    // Creating message element.
    const msgContainerEle = $('<div class="alert alert-info alert-dismissible fade show" id="message-container" role="alert"> </div>')
    const msgTextEle = $('<span id="msg-text"></span>')
    const msgContainerClsBtnEle = $('<button type="button" class="btn-close" data-ns-dismiss="alert" aria-label="Close"></button>')
    msgContainerEle.append(msgTextEle)
    msgContainerEle.append(msgContainerClsBtnEle)

    // Placing message element
    const alertShowEle = mediaUploadForm.find('#alert_info')
    alertShowEle.append(msgContainerEle)

    // Making alert area hidden in table row
    const alertShowParentRowEle = alertShowEle.parent().parent()
    alertShowParentRowEle.prop('hidden', true)
    
    const mediaFormAjaxMessageDiv = msgContainerEle.find('#msg-text');

    if (closeButton.length) {
        closeButton.on('click', function(){hidePopup(mediaUploadModalContainer)});
    }

    mediaUploadForm.find(msgContainerClsBtnEle).on('click', function(){
        alertShowParentRowEle.prop('hidden', true)
    });

    // Changing file accept format
    const formFileInputEle = mediaUploadForm.find('input[name="file"]')
    const formFileTypeEle = mediaUploadForm.find('select[name="type"]')
    formFileInputEle.prop('disabled', true)
    formFileTypeEle.on('change', function(){
        formFileUploadType = formFileTypeEle.val() // ex: image, video
        if (formFileUploadType){
            formFileInputEle.prop('accept', formFileUploadType+'/*');
            formFileInputEle.prop('disabled', false);
        } else {
            formFileInputEle.prop('disabled', true);
            formFileInputEle.prop('accept', ''); 
        }
    });

    if (mediaUploadForm.length) {
        mediaUploadForm.on('submit', function(e) {
            e.preventDefault();

            const formData = new FormData(this);
            const csrfToken = formData.get('csrfmiddlewaretoken');

            if (mediaFormAjaxMessageDiv.length) {
                mediaFormAjaxMessageDiv.text('').removeClass('text-success text-danger');
            }
            mediaUploadForm.find('.text-danger').html(''); // Clear existing field errors

            // Make an AJAX POST request to submit the form data
            $.ajax({
                url: "/mediaUpload",
                method: 'POST',
                data: formData,
                processData: false, // Important: Don't process the files
                contentType: false, // Important: Let FormData set content type for multipart
                headers: {
                    'X-CSRFToken': csrfToken, // Send CSRF token in header
                    'X-Requested-With': 'XMLHttpRequest'
                },
                success: function(data) {
                    if (data.status === 'success') {
                        if (mediaFormAjaxMessageDiv.length) {
                            mediaFormAjaxMessageDiv.addClass('text-success').text(data.message);
                            // making the alert message row visible
                            alertShowParentRowEle.prop('hidden', false);
                        }
                        
                    } 
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    let errorMessage = 'An unexpected error occurred. Please try again.';
                    if (jqXHR.responseJSON && jqXHR.responseJSON.errors) {
                        errorMessage = jqXHR.responseJSON;
                    } else if (errorThrown) {
                        errorMessage = errorThrown;
                    }
                    if (mediaFormAjaxMessageDiv.length) {
                        mediaFormAjaxMessageDiv.addClass('text-danger').text(jqXHR.responseText);
                        // making the alert message row visible
                        alertShowParentRowEle.prop('hidden', false);
                    }

                    if (errorMessage.errors) {
                        for (const fieldName in errorMessage.errors) {
                            const errorMessages = errorMessage.errors[fieldName];
                            // Find the input field by its name (assuming Django's default form field names)
                            const fieldInput = mediaUploadForm.find(`[name="${fieldName}"]`);
                            if (fieldInput.length) {
                                // Try to find an existing error div next to the input
                                let errorDiv = fieldInput.parent().siblings('.text-danger');
                                if (!errorDiv.length) {
                                    // If no existing error div, create one and insert it after the input
                                    errorDiv = $('<div class="text-danger"></div>');
                                    fieldInput.parent().after(errorDiv);
                                }
                                // Set the HTML of the error div with small tags for each message
                                errorDiv.html(errorMessages.map(msg => `<small>${msg}</small>`).join(''));
                            }
                        }
                    }
                }
            });
        });
    } else {
        console.warn("Form not found in loaded content.");
    }
}