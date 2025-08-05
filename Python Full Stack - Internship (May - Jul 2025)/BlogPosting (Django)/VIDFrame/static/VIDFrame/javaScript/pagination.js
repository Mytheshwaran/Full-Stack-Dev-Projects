// No block scope
var currentPage = 1; 
var totalPages = 0;
var rowsPerPage = 6; 
var paginationControls = $('#pagination-controls');

function createTablePagination(tableEle){
    
    const tableBody = tableEle.find('tbody');
    const allRows = tableBody.find('tr');
    const totalRows = allRows.length;
    
    totalPages = Math.ceil(totalRows / rowsPerPage);

    setupPagination(allRows);
    showPage(allRows,currentPage);
}

function createDivPagination(divEle){
    
    const allItems = divEle.children("#paginated-item");
    const totalItems = allItems.length;
    totalPages = Math.ceil(totalItems / rowsPerPage);

    setupPagination(allItems);
    showPage(allItems,currentPage);
}

function showPage(allItems, page) {
    allItems.hide();
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    allItems.slice(startIndex, endIndex).show();
    paginationControls.find('.page-item').removeClass('active');
    paginationControls.find(`.page-item[data-page="${page}"]`).addClass('active');

    if (page === 1) {
        $('#prev-page').addClass('disabled');
    } else {
        $('#prev-page').removeClass('disabled');
    }

    if (page === totalPages) {
        $('#next-page').addClass('disabled');
    } else {
        $('#next-page').removeClass('disabled');
    }
}

function setupPagination(allItems) {
    paginationControls.empty(); 
    paginationControls.append(`
        <li class="page-item" id="prev-page">
            <a class="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    `);
    for (let i = 1; i <= totalPages; i++) {
        paginationControls.append(`
            <li class="page-item" data-page="${i}">
                <a class="page-link" href="#">${i}</a>
            </li>
        `);
    }
    paginationControls.append(`
        <li class="page-item" id="next-page">
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    `);
    paginationControls.on('click', '.page-link', function(e) {
        e.preventDefault(); 

        const clickedListItem = $(this).closest('.page-item');
        if (clickedListItem.hasClass('disabled')) {
            return;
        }
        if (clickedListItem.attr('id') === 'prev-page') {
            if (currentPage > 1) {
                currentPage--;
            }
        } else if (clickedListItem.attr('id') === 'next-page') {
            if (currentPage < totalPages) {
                currentPage++;
            }
        } else {
            currentPage = parseInt(clickedListItem.data('page'));
        }
        showPage(allItems,currentPage);
    });
}