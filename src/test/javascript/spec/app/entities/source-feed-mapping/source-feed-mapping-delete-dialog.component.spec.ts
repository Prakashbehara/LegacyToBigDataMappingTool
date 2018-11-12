/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedMappingDeleteDialogComponent } from 'app/entities/source-feed-mapping/source-feed-mapping-delete-dialog.component';
import { SourceFeedMappingService } from 'app/entities/source-feed-mapping/source-feed-mapping.service';

describe('Component Tests', () => {
    describe('SourceFeedMapping Management Delete Component', () => {
        let comp: SourceFeedMappingDeleteDialogComponent;
        let fixture: ComponentFixture<SourceFeedMappingDeleteDialogComponent>;
        let service: SourceFeedMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedMappingDeleteDialogComponent]
            })
                .overrideTemplate(SourceFeedMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceFeedMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceFeedMappingService);
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
