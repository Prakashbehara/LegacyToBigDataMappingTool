import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';
import { DataModelMappingService } from './data-model-mapping.service';

@Component({
    selector: 'jhi-data-model-mapping-delete-dialog',
    templateUrl: './data-model-mapping-delete-dialog.component.html'
})
export class DataModelMappingDeleteDialogComponent {
    dataModelMapping: IDataModelMapping;

    constructor(
        private dataModelMappingService: DataModelMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataModelMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataModelMappingListModification',
                content: 'Deleted an dataModelMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-model-mapping-delete-popup',
    template: ''
})
export class DataModelMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataModelMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataModelMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dataModelMapping = dataModelMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
