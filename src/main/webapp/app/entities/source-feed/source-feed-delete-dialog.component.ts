import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceFeed } from 'app/shared/model/source-feed.model';
import { SourceFeedService } from './source-feed.service';

@Component({
    selector: 'jhi-source-feed-delete-dialog',
    templateUrl: './source-feed-delete-dialog.component.html'
})
export class SourceFeedDeleteDialogComponent {
    sourceFeed: ISourceFeed;

    constructor(private sourceFeedService: SourceFeedService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceFeedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceFeedListModification',
                content: 'Deleted an sourceFeed'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-feed-delete-popup',
    template: ''
})
export class SourceFeedDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceFeed }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceFeedDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sourceFeed = sourceFeed;
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
