import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from './source-database.service';

@Component({
    selector: 'jhi-source-database-delete-dialog',
    templateUrl: './source-database-delete-dialog.component.html'
})
export class SourceDatabaseDeleteDialogComponent {
    sourceDatabase: ISourceDatabase;

    constructor(
        private sourceDatabaseService: SourceDatabaseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceDatabaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceDatabaseListModification',
                content: 'Deleted an sourceDatabase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-database-delete-popup',
    template: ''
})
export class SourceDatabaseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDatabase }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceDatabaseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sourceDatabase = sourceDatabase;
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
