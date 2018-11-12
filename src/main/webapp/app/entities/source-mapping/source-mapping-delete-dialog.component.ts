import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceMapping } from 'app/shared/model/source-mapping.model';
import { SourceMappingService } from './source-mapping.service';

@Component({
    selector: 'jhi-source-mapping-delete-dialog',
    templateUrl: './source-mapping-delete-dialog.component.html'
})
export class SourceMappingDeleteDialogComponent {
    sourceMapping: ISourceMapping;

    constructor(
        private sourceMappingService: SourceMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceMappingListModification',
                content: 'Deleted an sourceMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-mapping-delete-popup',
    template: ''
})
export class SourceMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sourceMapping = sourceMapping;
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
