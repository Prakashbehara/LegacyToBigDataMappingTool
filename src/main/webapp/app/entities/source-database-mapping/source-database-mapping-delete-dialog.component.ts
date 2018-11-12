import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';
import { SourceDatabaseMappingService } from './source-database-mapping.service';

@Component({
    selector: 'jhi-source-database-mapping-delete-dialog',
    templateUrl: './source-database-mapping-delete-dialog.component.html'
})
export class SourceDatabaseMappingDeleteDialogComponent {
    sourceDatabaseMapping: ISourceDatabaseMapping;

    constructor(
        private sourceDatabaseMappingService: SourceDatabaseMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceDatabaseMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceDatabaseMappingListModification',
                content: 'Deleted an sourceDatabaseMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-database-mapping-delete-popup',
    template: ''
})
export class SourceDatabaseMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDatabaseMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceDatabaseMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sourceDatabaseMapping = sourceDatabaseMapping;
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
