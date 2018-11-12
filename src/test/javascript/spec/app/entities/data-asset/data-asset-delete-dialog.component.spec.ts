/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { DataAssetDeleteDialogComponent } from 'app/entities/data-asset/data-asset-delete-dialog.component';
import { DataAssetService } from 'app/entities/data-asset/data-asset.service';

describe('Component Tests', () => {
    describe('DataAsset Management Delete Component', () => {
        let comp: DataAssetDeleteDialogComponent;
        let fixture: ComponentFixture<DataAssetDeleteDialogComponent>;
        let service: DataAssetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataAssetDeleteDialogComponent]
            })
                .overrideTemplate(DataAssetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataAssetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataAssetService);
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
