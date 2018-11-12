/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { SourceMappingDeleteDialogComponent } from 'app/entities/source-mapping/source-mapping-delete-dialog.component';
import { SourceMappingService } from 'app/entities/source-mapping/source-mapping.service';

describe('Component Tests', () => {
    describe('SourceMapping Management Delete Component', () => {
        let comp: SourceMappingDeleteDialogComponent;
        let fixture: ComponentFixture<SourceMappingDeleteDialogComponent>;
        let service: SourceMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceMappingDeleteDialogComponent]
            })
                .overrideTemplate(SourceMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceMappingService);
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
