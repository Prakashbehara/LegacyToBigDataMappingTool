/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedDeleteDialogComponent } from 'app/entities/source-feed/source-feed-delete-dialog.component';
import { SourceFeedService } from 'app/entities/source-feed/source-feed.service';

describe('Component Tests', () => {
    describe('SourceFeed Management Delete Component', () => {
        let comp: SourceFeedDeleteDialogComponent;
        let fixture: ComponentFixture<SourceFeedDeleteDialogComponent>;
        let service: SourceFeedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedDeleteDialogComponent]
            })
                .overrideTemplate(SourceFeedDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceFeedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceFeedService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
