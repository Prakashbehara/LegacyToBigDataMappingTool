import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';
import { SourceFeedMappingService } from './source-feed-mapping.service';

@Component({
    selector: 'jhi-source-feed-mapping-delete-dialog',
    templateUrl: './source-feed-mapping-delete-dialog.component.html'
})
export class SourceFeedMappingDeleteDialogComponent {
    sourceFeedMapping: ISourceFeedMapping;

    constructor(
        private sourceFeedMappingService: SourceFeedMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceFeedMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceFeedMappingListModification',
                content: 'Deleted an sourceFeedMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-feed-mapping-delete-popup',
    template: ''
})
export class SourceFeedMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceFeedMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceFeedMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sourceFeedMapping = sourceFeedMapping;
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
